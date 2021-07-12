package com.lollipop.e_lapor.view.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lollipop.e_lapor.databinding.FragmentDetailAduanBinding
import com.lollipop.e_lapor.service.model.Aduan
import com.lollipop.e_lapor.util.Constant
import com.lollipop.e_lapor.util.DateFormatLocale
import com.lollipop.e_lapor.util.ResultOfNetwork
import com.lollipop.e_lapor.view.ui.MainActivity
import com.lollipop.e_lapor.viewModel.DataStoreViewModel
import com.lollipop.e_lapor.viewModel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber
import java.io.File


@FlowPreview
@ExperimentalCoroutinesApi
@RequiresApi(Build.VERSION_CODES.M)
class DetailAduanFragment : Fragment() {

    val CAMERA_CODE = 10
    val GALLERY_CODE = 11

    private var _bindingFragment: FragmentDetailAduanBinding? = null
    private val _binding get() = _bindingFragment!!

    private lateinit var _viewModelDataStore: DataStoreViewModel
    private lateinit var _viewModel: MainViewModel

    private var _nik = ""

    private lateinit var filePhoto: File
    private val FILE_NAME = DateFormatLocale.getDateTimeNow()

    private lateinit var _uri: Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingFragment = FragmentDetailAduanBinding.inflate(layoutInflater)
        initializeViewModel()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(_binding){
            btCamera.setOnClickListener {
                openCameraForImage()
            }

            btGallery.setOnClickListener {
                openGalleryForImage()
            }

            btKirim.setOnClickListener {
                _viewModel.kirim("input_aduan", Aduan(
                    _nik,
                    "novi.jpg",
                    "${etPesan.text}",
                    "${etNotelp.text}",
                    "111",
                    "222",
                    "Sampah",
                    "1"
                ))
            }
        }

        observableLiveData()
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_CODE)
        (activity as MainActivity).isDetailOpen = true
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openCameraForImage() {
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        filePhoto = getPhotoFile(FILE_NAME)
        _uri = Uri.fromFile(filePhoto)

        val providerFile =FileProvider.getUriForFile(requireActivity(),"com.lollipop.e_lapor.fileprovider", filePhoto)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)
        if (takePhotoIntent.resolveActivity(requireActivity().packageManager) != null){
            startActivityForResult(takePhotoIntent, CAMERA_CODE)
            (activity as MainActivity).isDetailOpen = true
        }else {
            Toast.makeText(requireActivity(),"Camera could not open", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getPhotoFile(fileName: String): File {
        val directoryStorage = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }

    private fun initializeViewModel() {
        _viewModelDataStore = ViewModelProvider(this).get(DataStoreViewModel::class.java)
        _viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun observableLiveData() {
        _viewModelDataStore.userData.observe(viewLifecycleOwner, {
            _nik = it[0]
        })

        _viewModel.kirimAduan.observe(viewLifecycleOwner, { result ->
            when(result){
                is ResultOfNetwork.Success -> {
                    result.value.code?.let { isSuccessNetworkCallback(it) }
                }
                is ResultOfNetwork.Failure -> {
                    Toast.makeText(requireActivity(), result.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK){
            Timber.d("liat image ${filePhoto.absolutePath}")
            val takenPhoto = BitmapFactory.decodeFile(filePhoto.absolutePath)
        }
        if(requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK){
            Timber.d("liat image ${data?.data}")
            _binding.ivImage.setImageURI(data?.data)
        }
    }

    private fun isSuccessNetworkCallback(code: Int) {
        when (code) {
            Constant.Network.REQUEST_NOT_FOUND -> {
                Toast.makeText(requireActivity(), "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
            }
            Constant.Network.REQUEST_SUCCESS -> {
                Toast.makeText(requireActivity(), "Berhasil menyimpan data", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
        }
    }
}