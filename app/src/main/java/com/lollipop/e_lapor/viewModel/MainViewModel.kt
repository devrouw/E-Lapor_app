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
    val dinasData: LiveData<ResultOfNetwork<DinasData>>

    init {
        this.daftarAkun = _repository.dataResult
        this.login = _repository.loginResult
        this.kirimAduan = _repository.aduanResult
        this.dinasData = _repository.dinasResult
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
                _repository.kirimAduan(case, aduan)
            }catch (throwable: Throwable){
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

    fun listDinas(case: String){
        viewModelScope.launch {
            try {
                _repository.listDinas(case)
            }catch (throwable: Throwable){
                when (throwable) {
                    is IOException -> _repository.dinasResult
                        .postValue(
                            ResultOfNetwork.Failure(
                                "[IO] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                    is HttpException -> {
                        _repository.dinasResult
                            .postValue(
                                ResultOfNetwork.Failure(
                                    "[HTTP] error ${throwable.message} please retry",
                                    throwable
                                )
                            )
                    }
                    else -> _repository.dinasResult
                        .postValue(ResultOfNetwork.Failure("[Unknown] error ${throwable.message} please retry", throwable))
                }
            }
        }
    }
}