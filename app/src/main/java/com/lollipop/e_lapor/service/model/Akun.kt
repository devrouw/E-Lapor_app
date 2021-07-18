package com.lollipop.e_lapor.service.model

data class Akun(
    val nik: String = "",
    val nama_lengkap: String = "",
    val tempat_lahir: String = "",
    val tanggal_lahir: String = "",
    val jenis_kelamin: String = "",
    val alamat: String = "",
    val email: String = "-",
    val no_telepon: String = "",
    val kode_pos: String = "",
    val kabupaten: String = "",
    val kecamatan: String = "",
    val kelurahan: String = "",
    val foto_profil: String = "",
    val nama_foto: String = "",
)