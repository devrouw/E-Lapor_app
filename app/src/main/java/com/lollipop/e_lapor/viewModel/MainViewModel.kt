package com.lollipop.e_lapor.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lollipop.e_lapor.repository.MainRepository
import com.lollipop.e_lapor.service.model.*
import com.lollipop.e_lapor.util.ResultOfNetwork
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainViewModel : ViewModel() {
    private val _repository = MainRepository()

    val daftarAkun: LiveData<ResultOfNetwork<KirimData>>
    val login: LiveData<ResultOfNetwork<LoginData>>
    val kirimAduan: LiveData<ResultOfNetwork<KirimData>>
    val aduanData: LiveData<ResultOfNetwork<AduanData>>
    val perbaikanData: LiveData<ResultOfNetwork<PerbaikanData>>
    val kategoriData: LiveData<ResultOfNetwork<KategoriData>>
    val progressBar: LiveData<Boolean>

    init {
        this.daftarAkun = _repository.dataResult
        this.login = _repository.loginResult
        this.kirimAduan = _repository.aduanResult
        this.aduanData = _repository.aduanList
        this.perbaikanData = _repository.perbaikanList
        this.kategoriData = _repository.kategoriResult
        this.progressBar = _repository.progressBar
    }

    fun loginAkun(case: String, email: String, password: String){
        viewModelScope.launch {
            try {
                _repository.loginAkun(case, email, password)
            }catch (throwable: Throwable){
                when (throwable) {
                    is IOException -> _repository.loginResult
                        .postValue(
                            ResultOfNetwork.Failure(
                                "[IO] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                    is HttpException -> {
                        _repository.loginResult
                            .postValue(
                                ResultOfNetwork.Failure(
                                    "[HTTP] error ${throwable.message} please retry",
                                    throwable
                                )
                            )
                    }
                    else -> _repository.loginResult
                        .postValue(ResultOfNetwork.Failure("[Unknown] error ${throwable.message} please retry", throwable))
                }
            }
        }
    }

    fun showAkun(case: String, nik: String){
        viewModelScope.launch {
            try {
                _repository.showAkun(case, nik)
            }catch (throwable: Throwable){
                when (throwable) {
                    is IOException -> _repository.loginResult
                        .postValue(
                            ResultOfNetwork.Failure(
                                "[IO] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                    is HttpException -> {
                        _repository.loginResult
                            .postValue(
                                ResultOfNetwork.Failure(
                                    "[HTTP] error ${throwable.message} please retry",
                                    throwable
                                )
                            )
                    }
                    else -> _repository.loginResult
                        .postValue(ResultOfNetwork.Failure("[Unknown] error ${throwable.message} please retry", throwable))
                }
            }
        }
    }

    fun editAkun(case: String, akun: Akun){
        viewModelScope.launch {
            try {
                _repository.editAkun(case, akun)
            }catch (throwable: Throwable){
                when (throwable) {
                    is IOException -> _repository.dataResult
                        .postValue(
                            ResultOfNetwork.Failure(
                                "[IO] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                    is HttpException -> {
                        _repository.dataResult
                            .postValue(
                                ResultOfNetwork.Failure(
                                    "[HTTP] error ${throwable.message} please retry",
                                    throwable
                                )
                            )
                    }
                    else -> _repository.dataResult
                        .postValue(ResultOfNetwork.Failure("[Unknown] error ${throwable.message} please retry", throwable))
                }
            }
        }
    }

    fun daftar(case: String, akun: Akun){
        viewModelScope.launch {
            try {
                _repository.daftarAkun(case, akun)
            }catch (throwable: Throwable){
                when (throwable) {
                    is IOException -> _repository.dataResult
                        .postValue(
                            ResultOfNetwork.Failure(
                                "[IO] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                    is HttpException -> {
                        _repository.dataResult
                            .postValue(
                                ResultOfNetwork.Failure(
                                    "[HTTP] error ${throwable.message} please retry",
                                    throwable
                                )
                            )
                    }
                    else -> _repository.dataResult
                        .postValue(ResultOfNetwork.Failure("[Unknown] error ${throwable.message} please retry", throwable))
                }
            }
        }
    }

    fun kirim(case: String, aduan: Aduan){
        viewModelScope.launch {
            try {
                _repository.progressBar.postValue(true)
                _repository.kirimAduan(case, aduan)
            }catch (throwable: Throwable){
                _repository.progressBar.postValue(false)
                when (throwable) {
                    is IOException -> _repository.aduanResult
                        .postValue(
                            ResultOfNetwork.Failure(
                                "[IO] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                    is HttpException -> {
                        _repository.aduanResult
                            .postValue(
                                ResultOfNetwork.Failure(
                                    "[HTTP] error ${throwable.message} please retry",
                                    throwable
                                )
                            )
                    }
                    else -> _repository.aduanResult
                        .postValue(ResultOfNetwork.Failure("[Unknown] error ${throwable.message} please retry", throwable))
                }
            }
        }
    }

    fun getListAduan(case: String, nik: String){
        viewModelScope.launch {
            try {
                _repository.listAduan(case, nik)
            }catch (throwable: Throwable){
                when (throwable) {
                    is IOException -> _repository.aduanList
                        .postValue(
                            ResultOfNetwork.Failure(
                                "[IO] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                    is HttpException -> {
                        _repository.aduanList
                            .postValue(
                                ResultOfNetwork.Failure(
                                    "[HTTP] error ${throwable.message} please retry",
                                    throwable
                                )
                            )
                    }
                    else -> _repository.aduanList
                        .postValue(ResultOfNetwork.Failure("[Unknown] error ${throwable.message} please retry", throwable))
                }
            }
        }
    }

    fun getListPerbaikan(case: String, nik: String){
        viewModelScope.launch {
            try {
                _repository.listPerbaikan(case, nik)
            }catch (throwable: Throwable){
                when (throwable) {
                    is IOException -> _repository.perbaikanList
                        .postValue(
                            ResultOfNetwork.Failure(
                                "[IO] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                    is HttpException -> {
                        _repository.perbaikanList
                            .postValue(
                                ResultOfNetwork.Failure(
                                    "[HTTP] error ${throwable.message} please retry",
                                    throwable
                                )
                            )
                    }
                    else -> _repository.perbaikanList
                        .postValue(ResultOfNetwork.Failure("[Unknown] error ${throwable.message} please retry", throwable))
                }
            }
        }
    }

    fun getDetailPerbaikan(case: String, nik: String, idAduan: String){
        viewModelScope.launch {
            try {
                _repository.detailPerbaikan(case, nik, idAduan)
            }catch (throwable: Throwable){
                when (throwable) {
                    is IOException -> _repository.perbaikanList
                        .postValue(
                            ResultOfNetwork.Failure(
                                "[IO] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                    is HttpException -> {
                        _repository.perbaikanList
                            .postValue(
                                ResultOfNetwork.Failure(
                                    "[HTTP] error ${throwable.message} please retry",
                                    throwable
                                )
                            )
                    }
                    else -> _repository.perbaikanList
                        .postValue(ResultOfNetwork.Failure("[Unknown] error ${throwable.message} please retry", throwable))
                }
            }
        }
    }

    fun getListKategori(case: String){
        viewModelScope.launch {
            try {
                _repository.listKategori(case)
            }catch (throwable: Throwable){
                when (throwable) {
                    is IOException -> _repository.kategoriResult
                        .postValue(
                            ResultOfNetwork.Failure(
                                "[IO] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                    is HttpException -> {
                        _repository.kategoriResult
                            .postValue(
                                ResultOfNetwork.Failure(
                                    "[HTTP] error ${throwable.message} please retry",
                                    throwable
                                )
                            )
                    }
                    else -> _repository.kategoriResult
                        .postValue(ResultOfNetwork.Failure("[Unknown] error ${throwable.message} please retry", throwable))
                }
            }
        }
    }
}