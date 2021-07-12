package com.lollipop.e_lapor.view.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lollipop.e_lapor.databinding.ActivityLoginBinding
import com.lollipop.e_lapor.service.model.LoginResult
import com.lollipop.e_lapor.util.Constant
import com.lollipop.e_lapor.util.ResultOfNetwork
import com.lollipop.e_lapor.viewModel.DataStoreViewModel
import com.lollipop.e_lapor.viewModel.MainViewModel

class LoginActivity:AppCompatActivity() {
    private lateinit var _binding: ActivityLoginBinding

    private lateinit var _viewModelDataStore: DataStoreViewModel
    private lateinit var _viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        initializeViewModel()

        with(_binding){
            btDaftar.setOnClickListener {
                startActivity(Intent(this@LoginActivity,DaftarActivity::class.java))
            }

            btMasuk.setOnClickListener {
                _viewModel.loginAkun(
                    "login",
                    "${etEmail.text}",
                    "${etPassword.text}"
                )
            }
        }

        observableLiveData()
    }

    private fun observableLiveData() {
        _viewModel.login.observe(this@LoginActivity, { result ->
            when(result){
                is ResultOfNetwork.Success -> {
                    result.value.code?.let { isSuccessNetworkCallback(it,result.value.data) }
                }
                is ResultOfNetwork.Failure -> {
                    Toast.makeText(this@LoginActivity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initializeViewModel() {
        _viewModelDataStore = ViewModelProvider(this).get(DataStoreViewModel::class.java)
        _viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun isSuccessNetworkCallback(code: Int, data: List<LoginResult>?) {
        when (code) {
            Constant.Network.REQUEST_NOT_FOUND -> {
                Toast.makeText(this@LoginActivity, "Email atau Password Salah", Toast.LENGTH_SHORT).show()
            }
            Constant.Network.REQUEST_SUCCESS -> {
                if (data != null) {
                    data.get(0).nik?.let { data.get(0).nama_lengkap?.let { it1 ->
                        data.get(0).no_telpon?.let { it2 ->
                            data.get(0).foto_profil?.let { it3 ->
                                _viewModelDataStore.setAuthPref(it,
                                    it1, it2, it3
                                )
                            }
                        }
                    } }
                }
                _viewModelDataStore.setLoginStatus(true)
                Toast.makeText(this@LoginActivity, "Berhasil Masuk", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}