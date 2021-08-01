package com.lollipop.e_lapor.view.ui.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lollipop.e_lapor.R
import com.lollipop.e_lapor.databinding.FragmentBiodataBinding
import com.lollipop.e_lapor.service.model.Akun
import com.lollipop.e_lapor.service.model.LoginResult
import com.lollipop.e_lapor.util.Constant
import com.lollipop.e_lapor.util.ImageUtils
import com.lollipop.e_lapor.util.ResultOfNetwork
import com.lollipop.e_lapor.viewModel.DataStoreViewModel
import com.lollipop.e_lapor.viewModel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File

class EditDaftarFragment : Fragment() {

    val REQUEST_CODE = 100

    private var _bindingFragment: FragmentBiodataBinding? = null
    private val _binding get() = _bindingFragment!!

    private lateinit var _viewModelDataStore: DataStoreViewModel
    private lateinit var _viewModel: MainViewModel
    private lateinit var _mContext: Context

    private var _nik = ""
    private var _imageBase = ""
    private var _imageName = ""
    private var pswd = "hide"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        _mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingFragment = FragmentBiodataBinding.inflate(inflater,container,false)
        initializeViewModel()
        return _binding.root
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(_binding){
            unggahFoto.setOnClickListener {
                openGalleryForImage()
            }

            btSimpan.setOnClickListener {
                _viewModel.editAkun("edit_biodata",Akun(
                    _nik,
                    "${etNamaLengkap.text}",
                    "${etTempatLahir.text}",
                    "${etYy.text}-${etMm.text}-${etDd.text}",
                    "${spJenisKelamin.selectedItem}",
                    "${etAlamat.text}",
                    "${etEmail.text}",
                    "${etPassword.text}",
                    "${etNoTelp.text}",
                    "${etKodePos.text}",
                    "${etKabupaten.text}",
                    "${etKecamatan.text}",
                    "${etKelurahan.text}",
                    _imageBase,
                    _imageName
                ))
            }

            imgHidePass.setOnClickListener {
                pswd = if (pswd == "hide") {
                    etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                    imgHidePass.setImageResource(R.drawable.ic_baseline_visibility_blue)
                    "show"
                } else {
                    etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    imgHidePass.setImageResource(R.drawable.ic_baseline_visibility_off_blue)
                    "hide"
                }
            }
        }

        observableLiveData()
    }

    private fun observableLiveData() {
        _viewModelDataStore.userData.observe(viewLifecycleOwner, {
            _viewModel.showAkun("biodata",it[0])
            _nik = it[0]
        })

        _viewModel.login.observe(viewLifecycleOwner, {
            when (it) {
                is ResultOfNetwork.Success -> {
                    isSuccessNetworkCallback(
                        it.value.code,
                        it.value.data
                    )
                }
                is ResultOfNetwork.Failure -> {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        _viewModel.daftarAkun.observe(viewLifecycleOwner, { result ->
            when (result) {
                is ResultOfNetwork.Success -> {
                    result.value.code?.let { isSuccessNetworkCallbackDaftar(it) }
                }
                is ResultOfNetwork.Failure -> {
                    Toast.makeText(requireActivity(), result.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initializeViewModel() {
        _viewModelDataStore = ViewModelProvider(this).get(DataStoreViewModel::class.java)
        _viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun isSuccessNetworkCallbackDaftar(code: Int) {
        when (code) {
            Constant.Network.REQUEST_NOT_FOUND -> {
                Toast.makeText(requireActivity(), "Gagal menyimpan data", Toast.LENGTH_SHORT)
                    .show()
            }
            Constant.Network.REQUEST_SUCCESS -> {
                Toast.makeText(requireActivity(), "Berhasil menyimpan data", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun isSuccessNetworkCallback(code: Int?, data: List<LoginResult>?) {
        when (code) {
            Constant.Network.REQUEST_NOT_FOUND -> {
                Timber.d("Tidak ada data")
            }
            Constant.Network.REQUEST_SUCCESS -> {
                _binding.etNik.setText(data?.get(0)?.nik)
                _binding.etNamaLengkap.setText(data?.get(0)?.nama_lengkap)
                _binding.etTempatLahir.setText(data?.get(0)?.tempat_lahir)
                _binding.etDd.setText(data?.get(0)?.tgl_lahir?.takeLast(2))
                _binding.etMm.setText(data?.get(0)?.tgl_lahir?.substring(5,7))
                _binding.etYy.setText(data?.get(0)?.tgl_lahir?.take(4))
                _binding.etAlamat.setText(data?.get(0)?.alamat)
                _binding.etEmail.setText(data?.get(0)?.email)
                _binding.etPassword.setText(data?.get(0)?.password)
                _binding.etNoTelp.setText(data?.get(0)?.no_telpon)
                _binding.etKodePos.setText(data?.get(0)?.kode_pos)
                _binding.etKabupaten.setText(data?.get(0)?.kabupaten)
                _binding.etKecamatan.setText(data?.get(0)?.kecamatan)
                _binding.etKelurahan.setText(data?.get(0)?.kelurahan)
                var _index = 0
                if(data?.get(0)?.jenis_kelamin.equals("Perempuan")){
                    _index = 0
                }else{
                    _index = 1
                }
                _binding.spJenisKelamin.setSelection(_index)
                _binding.unggahFoto.text = data?.get(0)?.foto_profil
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            val filePath = data?.data?.let { getRealPathFromURI(it) }
            val bitmap: Bitmap = ImageUtils.getInstant().getCompressedBitmap(filePath)
            val output1 = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, output1)
            val byte_arr1 = output1.toByteArray()
            _imageBase = Base64.encodeToString(byte_arr1, Base64.DEFAULT)
            if (filePath != null) {
                _imageName = File(data.data!!.path).name
            }
            Timber.d("filepath ${filePath} filename ${_imageName}")
            _binding.unggahFoto.text = _imageName
        }
    }

    fun getRealPathFromURI(contentUri: Uri): String? {
        var path: String? = null
        val proj = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor: Cursor? = contentUri.let {
            requireActivity().getContentResolver()
                .query(it, proj, null, null, null)
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                path = cursor.getString(column_index)
            }
        }
        if (cursor != null) {
            cursor.close()
        }
        return path
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }
}