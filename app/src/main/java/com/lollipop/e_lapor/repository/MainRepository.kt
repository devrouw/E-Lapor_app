package com.lollipop.e_lapor.repository

import androidx.lifecycle.MutableLiveData
import com.lollipop.e_lapor.service.model.*
import com.lollipop.e_lapor.service.network.RetrofitClient
import com.lollipop.e_lapor.util.ResultOfNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository() {
    val dataResult = MutableLiveData<ResultOfNetwork<KirimData>>()
    val loginResult = MutableLiveData<ResultOfNetwork<LoginData>>()
    val aduanResult = MutableLiveData<ResultOfNetwork<KirimData>>()
    val dinasResult = MutableLiveData<ResultOfNetwork<DinasData>>()

    suspend fun daftarAkun(case: String, akun: Akun) =
        withContext(Dispatchers.IO){
            dataResult.postValue(ResultOfNetwork.Success(
                RetrofitClient.ftp.daftar(case, akun.nik, akun.nama_lengkap, akun.tempat_lahir, akun.tanggal_lahir,
                akun.jenis_kelamin, akun.alamat, akun.email, akun.no_telepon, akun.kode_pos, akun.kabupaten,
                akun.kecamatan, akun.kelurahan, akun.foto_profil)
            ))
        }

    suspend fun loginAkun(case: String, email: String, password: String) =
        loginResult.postValue(ResultOfNetwork.Success(
            RetrofitClient.ftp.login(case,email, password)
        ))

    suspend fun kirimAduan(case: String, aduan: Aduan){
        withContext(Dispatchers.IO){
            aduanResult.postValue(ResultOfNetwork.Success(
                RetrofitClient.ftp.inputAduan(case,aduan.nik,aduan.foto_aduan,aduan.pesan,aduan.no_telpon,
                    aduan.lng,aduan.lat,aduan.kategori,aduan.id_dinas)
            ))
        }
    }

    suspend fun listDinas(case: String){
        withContext(Dispatchers.IO){
            dinasResult.postValue(ResultOfNetwork.Success(
                RetrofitClient.ftp.listDinas(case)
            ))
        }
    }
}