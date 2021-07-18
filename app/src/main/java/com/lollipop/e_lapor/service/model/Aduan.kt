package com.lollipop.e_lapor.service.model

data class Aduan(
    val nik: String = "",
    val foto_aduan: String = "",
    val nama_foto: String = "",
    val pesan: String = "",
    val no_telpon: String = "",
    val lng: String = "",
    val lat: String = "",
    val kategori: String = "-",
    val id_dinas: String = "",
)
