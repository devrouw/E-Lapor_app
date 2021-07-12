package com.lollipop.e_lapor.view.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lollipop.e_lapor.databinding.ActivityDaftarBinding
import com.lollipop.e_lapor.service.model.Akun
import com.lollipop.e_lapor.util.Constant
import com.lollipop.e_lapor.util.ResultOfNetwork
import com.lollipop.e_lapor.viewModel.MainViewModel
import com.lollipop.e_lapor.service.model.Result
import timber.log.Timber
import java.io.File

@Suppress("DEPRECATION")
class DaftarActivity : AppCompatActivity() {

    val REQUEST_CODE = 100
    private lateinit var _binding: ActivityDaftarBinding

    private lateinit var _viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDaftarBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        initializeViewModel()

        with(_binding){
            unggahFoto.setOnClickListener {
                openGalleryForImage()
            }
            btDaftar.setOnClickListener {
                _viewModel.daftar("daftar", Akun(
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
                    "Foto.JPG"
                ))
            }
        }

        observableLiveData()

    }

    private fun observableLiveData() {
        _viewModel.daftarAkun.observe(this@DaftarActivity, { result ->
            when(result){
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
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            val filename = File(data?.data?.path).name
            _binding.unggahFoto.text = filename
            Timber.d("Liat data ${data?.data} ${filename}")
        }
    }

    private fun isSuccessNetworkCallback(code: Int) {
        when (code) {
            Constant.Network.REQUEST_NOT_FOUND -> {
                Toast.makeText(this@DaftarActivity, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }
            Constant.Network.REQUEST_SUCCESS -> {
                Toast.makeText(this@DaftarActivity, "Berhasil menyimpan data", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

}