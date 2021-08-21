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
    val aduanList = MutableLiveData<ResultOfNetwork<AduanData>>()
    val perbaikanList = MutableLiveData<ResultOfNetwork<PerbaikanData>>()
    val kategoriResult = MutableLiveData<ResultOfNetwork<KategoriData>>()
    val progressBar = MutableLiveData<Boolean>()

    suspend fun daftarAkun(case: String, akun: Akun) =
        withContext(Dispatchers.IO){
            dataResult.postValue(ResultOfNetwork.Success(
                RetrofitClient.ftp.daftar(case, akun.nik, akun.nama_lengkap, akun.tempat_lahir, akun.tanggal_lahir,
                akun.jenis_kelamin, akun.alamat, akun.email, akun.no_telepon, akun.kode_pos, akun.kabupaten,
                akun.kecamatan, akun.kelurahan, akun.foto_profil, akun.nama_foto)
            ))
        }

    suspend fun loginAkun(case: String, email: String, password: String) =
        loginResult.postValue(ResultOfNetwork.Success(
            RetrofitClient.ftp.login(case,email, password)
        ))

    suspend fun showAkun(case: String, nik: String) =
        loginResult.postValue(ResultOfNetwork.Success(
            RetrofitClient.ftp.biodata(case,nik)
        ))

    suspend fun editAkun(case: String, akun: Akun) =
        dataResult.postValue(ResultOfNetwork.Success(
            RetrofitClient.ftp.editBiodata(case,akun.nik,akun.nama_lengkap, akun.tempat_lahir, akun.tanggal_lahir,
                akun.jenis_kelamin, akun.alamat, akun.email, akun.password,akun.no_telepon, akun.kode_pos, akun.kabupaten,
                akun.kecamatan, akun.kelurahan, akun.foto_profil, akun.nama_foto)
        ))

    suspend fun kirimAduan(case: String, aduan: Aduan){
        withContext(Dispatchers.IO){
            aduanResult.postValue(ResultOfNetwork.Success(
                RetrofitClient.ftp.inputAduan(case,aduan.nik,aduan.nama_foto,aduan.foto_aduan,aduan.pesan,aduan.no_telpon,
                    aduan.lng,aduan.lat,aduan.kategori,aduan.id_dinas)
            ))
            progressBar.postValue(false)
        }
    }

    suspend fun listAduan(case: String, nik: String){
        withContext(Dispatchers.IO){
            aduanList.postValue(ResultOfNetwork.Success(
                RetrofitClient.ftp.listAduan(case, nik)
            ))
        }
    }

    suspend fun listPerbaikan(case: String, nik: String){
        withContext(Dispatchers.IO){
            perbaikanList.postValue(ResultOfNetwork.Success(
                RetrofitClient.ftp.listPerbaikan(case, nik)
            ))
        }
    }

    suspend fun detailPerbaikan(case: String, nik: String, idAduan: String){
        withContext(Dispatchers.IO){
            perbaikanList.postValue(ResultOfNetwork.Success(
                RetrofitClient.ftp.detailPerbaikan(case, nik, idAduan)
            ))
        }
    }

    suspend fun listKategori(case: String){
        withContext(Dispatchers.IO){
            kategoriResult.postValue(ResultOfNetwork.Success(
                RetrofitClient.ftp.listKategori(case)
            ))
        }
    }
}