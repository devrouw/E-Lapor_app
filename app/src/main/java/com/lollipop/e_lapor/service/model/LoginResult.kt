package com.lollipop.e_lapor.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResult(
    val id: String? = "-",
    val email: String? = "-",
    val password: String? = "-",
    val nik: String? = "-",
    val nama_lengkap: String? = "-",
    val tempat_lahir: String? = "-",
    val tgl_lahir: String? = "-",
    val jenis_kelamin: String? = "-",
    val alamat: String? = "-",
    val no_telpon: String? = "-",
    val kode_pos: String? = "-",
    val kabupaten: String? = "-",
    val kecamatan: String? = "-",
    val kelurahan: String? = "-",
    val foto_profil: String? = "-",
)
