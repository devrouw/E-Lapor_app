package com.lollipop.e_lapor.service.network

import com.lollipop.e_lapor.service.model.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ShaNetwork {
    @FormUrlEncoded
    @POST("api.php")
    suspend fun daftar(
        @Field("case") case : String,
        @Field("nik") nik : String,
        @Field("nama_lengkap") namaLengkap : String,
        @Field("tempat_lahir") tempatLahir : String,
        @Field("tanggal_lahir") tanggalLahir : String,
        @Field("jenis_kelamin") jenisKelamin : String,
        @Field("alamat") alamat : String,
        @Field("email") email : String,
        @Field("no_telepon") noTelepon : String,
        @Field("kode_pos") kodePos : String,
        @Field("kabupaten") kabupaten : String,
        @Field("kecamatan") kecamatan : String,
        @Field("kelurahan") kelurahan : String,
        @Field("foto_profil") fotoProfil : String,
        @Field("nama_foto") namaFoto : String
    ) : KirimData

    @FormUrlEncoded
    @POST("api.php")
    suspend fun login(
        @Field("case") case : String,
        @Field("email") email : String,
        @Field("password") password : String
    ) : LoginData

    @FormUrlEncoded
    @POST("api.php")
    suspend fun biodata(
        @Field("case") case : String,
        @Field("nik") nik : String
    ) : LoginData

    @FormUrlEncoded
    @POST("api.php")
    suspend fun editBiodata(
        @Field("case") case : String,
        @Field("nik") nik : String,
        @Field("nama_lengkap") namaLengkap : String,
        @Field("tempat_lahir") tempatLahir : String,
        @Field("tanggal_lahir") tanggalLahir : String,
        @Field("jenis_kelamin") jenisKelamin : String,
        @Field("alamat") alamat : String,
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("no_telepon") noTelepon : String,
        @Field("kode_pos") kodePos : String,
        @Field("kabupaten") kabupaten : String,
        @Field("kecamatan") kecamatan : String,
        @Field("kelurahan") kelurahan : String,
        @Field("foto_profil") fotoProfil : String,
        @Field("nama_foto") namaFoto : String
    ) : KirimData

    @FormUrlEncoded
    @POST("api.php")
    suspend fun inputAduan(
        @Field("case") case : String,
        @Field("nik") nik : String,
        @Field("nama_foto") namaFoto : String,
        @Field("foto_aduan") fotoAduan : String,
        @Field("pesan") pesan : String,
        @Field("no_telpon") noTelpon : String,
        @Field("lng") lng : String,
        @Field("lat") lat : String,
        @Field("kategori") kategori : String,
        @Field("id_dinas") id_dinas : String
    ) : KirimData

    @FormUrlEncoded
    @POST("api.php")
    suspend fun listAduan(
        @Field("case") case : String,
        @Field("nik") nik : String
    ) : AduanData

    @FormUrlEncoded
    @POST("api.php")
    suspend fun listPerbaikan(
        @Field("case") case : String,
        @Field("nik") nik : String
    ) : PerbaikanData

    @FormUrlEncoded
    @POST("api.php")
    suspend fun detailPerbaikan(
        @Field("case") case : String,
        @Field("nik") nik : String,
        @Field("id_aduan") idAduan : String
    ) : PerbaikanData
}