package com.lollipop.e_lapor.view.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.lollipop.e_lapor.databinding.ActivityDetailAduanBinding
import com.lollipop.e_lapor.service.model.Aduan
import com.lollipop.e_lapor.service.model.DinasData
import com.lollipop.e_lapor.service.model.DinasResult
import com.lollipop.e_lapor.util.Constant
import com.lollipop.e_lapor.util.DateFormatLocale
import com.lollipop.e_lapor.util.ResultOfNetwork
import com.lollipop.e_lapor.viewModel.DataStoreViewModel
import com.lollipop.e_lapor.viewModel.MainViewModel
import timber.log.Timber
import java.io.File


class DetailAduanActivity : AppCompatActivity() {

    val CAMERA_CODE = 10
    val GALLERY_CODE = 11
    val LAUNCH_MAP_ACTIVITY = 100

    private lateinit var _binding: ActivityDetailAduanBinding

    private lateinit var _viewModelDataStore: DataStoreViewModel
    private lateinit var _viewModel: MainViewModel

    private var _nik = ""
    private var _lng = ""
    private var _lat = ""

    private lateinit var filePhoto: File
    private val FILE_NAME = DateFormatLocale.getDateTimeNow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailAduanBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        initializeViewModel()

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
                    _lng,
                    _lat,
                    spKategori.selectedItem.toString(),
                    "${(spKategori.selectedItemPosition+1)}"
                )
                )
            }

            ivMap.setOnClickListener {
                startActivityForResult(Intent(this@DetailAduanActivity,MapActivity2::class.java),LAUNCH_MAP_ACTIVITY)
            }

        }

        observableLiveData()
    }


    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_CODE)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openCameraForImage() {
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        filePhoto = getPhotoFile(FILE_NAME)

        val providerFile = FileProvider.getUriForFile(this,"com.lollipop.e_lapor.fileprovider", filePhoto)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)
        if (takePhotoIntent.resolveActivity(this.packageManager) != null){
            startActivityForResult(takePhotoIntent, CAMERA_CODE)
        }else {
            Toast.makeText(this,"Camera could not open", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getPhotoFile(fileName: String): File {
        val directoryStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }

    private fun initializeViewModel() {
        _viewModelDataStore = ViewModelProvider(this).get(DataStoreViewModel::class.java)
        _viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun observableLiveData() {
        _viewModelDataStore.userData.observe(this, {
            _nik = it[0]
        })

        _viewModel.kirimAduan.observe(this, { result ->
            when(result){
                is ResultOfNetwork.Success -> {
                    result.value.code?.let { isSuccessNetworkCallback(it) }
                }
                is ResultOfNetwork.Failure -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK){
            Timber.d("liat image ${filePhoto.absolutePath}")
            val takenPhoto = BitmapFactory.decodeFile(filePhoto.absolutePath)
            _binding.ivImage.setImageBitmap(takenPhoto)
        }
        if(requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK){
            Timber.d("liat image ${data?.data}")
            _binding.ivImage.setImageURI(data?.data)
        }
        if(requestCode == LAUNCH_MAP_ACTIVITY && resultCode == Activity.RESULT_OK){
            _lng = data?.getStringExtra("lng").toString()
            _lat = data?.getStringExtra("lat").toString()
            _binding.etLokasi.setText("${_lat},\n${_lng}")
        }
    }

//    private fun isSuccessNetworkDinas(code: Int, data: List<DinasResult>?) {
//        when (code) {
//            Constant.Network.REQUEST_NOT_FOUND -> {
//                Toast.makeText(this, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
//            }
//            Constant.Network.REQUEST_SUCCESS -> {
//                val adapter: ArrayAdapter<DinasResult> = ArrayAdapter<DinasResult>(
//                    this,
//                    android.R.layout.simple_spinner_item, data
//                )
//                _binding.spDinas.adapter = adapter
//            }
//        }
//    }

    private fun isSuccessNetworkCallback(code: Int) {
        when (code) {
            Constant.Network.REQUEST_NOT_FOUND -> {
                Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
            }
            Constant.Network.REQUEST_SUCCESS -> {
                Toast.makeText(this, "Berhasil menyimpan data", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

}