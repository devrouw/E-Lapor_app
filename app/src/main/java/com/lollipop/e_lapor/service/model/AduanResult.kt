package com.lollipop.e_lapor.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AduanResult(
    val id: String? = "-",
    val foto_aduan: String? = "-",
    val pesan: String? = "-",
    val no_telpon: String? = "-",
    val lng: String? = "-",
    val lat: String? = "-",
    val kategori: String? = "-",
    val id_dinas: String? = "-",
    val nik: String? = "-",
    val status: String? = "-",
    val id_perbaikan: String? = "-",
)
