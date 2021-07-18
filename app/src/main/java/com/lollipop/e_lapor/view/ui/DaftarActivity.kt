package com.lollipop.e_lapor.view.ui

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.ProgressDialog.show
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.lollipop.e_lapor.databinding.ActivityDaftarBinding
import com.lollipop.e_lapor.service.model.Akun
import com.lollipop.e_lapor.util.Constant
import com.lollipop.e_lapor.util.ImageUtils
import com.lollipop.e_lapor.util.ResultOfNetwork
import com.lollipop.e_lapor.viewModel.MainViewModel
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File

@Suppress("DEPRECATION")
class DaftarActivity : AppCompatActivity() {

    val REQUEST_CODE = 100
    private val PERMISSION_CODE = 1001
    private lateinit var _binding: ActivityDaftarBinding

    private lateinit var _viewModel: MainViewModel

    private var _imageBase = ""
    private var _imageName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDaftarBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        initializeViewModel()

        with(_binding) {
            unggahFoto.setOnClickListener {
                //check permission at runtime
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, PERMISSION_CODE)
                    } else{
                        openGalleryForImage()
                    }
                }else{
                    openGalleryForImage();
                }
            }

            btDaftar.setOnClickListener {
                _viewModel.daftar(
                    "daftar", Akun(
                        "${etNik.text}",
                        "${etNamaLengkap.text}",
                        "${etTempatLahir.text}",
                        "${etYy.text}-${etMm.text}-${etDd.text}",
                        "${spJenisKelamin.selectedItem}",
                        "${etAlamat.text}",
                        "${etEmail.text}",
                        "${etNoTelp.text}",
                        "${etKodePos.text}",
                        "${etKabupaten.text}",
                        "${etKecamatan.text}",
                        "${etKelurahan.text}",
                        _imageBase,
                        _imageName
                    )
                )
            }
        }

        observableLiveData()

    }

    private fun observableLiveData() {
        _viewModel.daftarAkun.observe(this@DaftarActivity, { result ->
            when (result) {
                is ResultOfNetwork.Success -> {
                    result.value.code?.let { isSuccessNetworkCallback(it) }
                }
                is ResultOfNetwork.Failure -> {
                    Toast.makeText(this@DaftarActivity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initializeViewModel() {
        _viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
//            if (Build.VERSION.SDK_INT >= 21) {
//                handleImageOnKitkat(data)
//            }
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

    private fun isSuccessNetworkCallback(code: Int) {
        when (code) {
            Constant.Network.REQUEST_NOT_FOUND -> {
                Toast.makeText(this@DaftarActivity, "Gagal mengambil data", Toast.LENGTH_SHORT)
                    .show()
            }
            Constant.Network.REQUEST_SUCCESS -> {
                Toast.makeText(this@DaftarActivity, "Berhasil menyimpan data", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openGalleryForImage()
                }else{
                    Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun getRealPathFromURI(contentUri: Uri): String? {
        var path: String? = null
        val proj = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor: Cursor? = contentUri.let {
            getApplicationContext().getContentResolver()
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

    private fun renderImage(imagePath: String?){
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            val output1 = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, output1)
            val byte_arr1 = output1.toByteArray()
            _imageBase = Base64.encodeToString(byte_arr1, Base64.DEFAULT)
            _imageName = imagePath
            Timber.d("image_URL ${_imageBase}")
        }
        else {
            Timber.d("ImagePath is null")
        }
    }

    private fun getImagePath(uri: Uri?, selection: String?): String {
        var path: String? = null
        val cursor = uri?.let { contentResolver.query(it, null, selection, null, null ) }
        if (cursor != null){
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }

    @TargetApi(19)
    private fun handleImageOnKitkat(data: Intent?) {
        var imagePath: String? = null
        val uri = data!!.data
        //DocumentsContract defines the contract between a documents provider and the platform.
        if (DocumentsContract.isDocumentUri(this, uri)){
            val docId = DocumentsContract.getDocumentId(uri)
            if (uri != null) {
                if ("com.android.providers.media.documents" == uri.authority){
                    val id = docId.split(":")[1]
                    val selsetion = MediaStore.Images.Media._ID + "=" + id
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        selsetion)
                } else if (uri != null) {
                    if ("com.android.providers.downloads.documents" == uri.authority){
                        val contentUri = ContentUris.withAppendedId(Uri.parse(
                            "content://downloads/public_downloads"), java.lang.Long.valueOf(docId))
                        imagePath = getImagePath(contentUri, null)
                    }
                }
            }
        }
        else if (uri != null) {
            if ("content".equals(uri.scheme, ignoreCase = true)){
                imagePath = getImagePath(uri, null)
            }
            else if ("file".equals(uri.scheme, ignoreCase = true)){
                imagePath = uri.path
            }
        }
        renderImage(imagePath)
    }

}