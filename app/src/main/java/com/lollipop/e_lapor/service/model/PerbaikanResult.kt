package com.lollipop.e_lapor.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PerbaikanResult(
    val id_perbaikan: String? = "-",
    val foto_perbaikan: String? = "-",
    val foto_aduan: String? = "-",
    val foto_profil: String? = "-",
    val pesan: String? = "-",
    val keterangan: String? = "-",
    val kategori: String? = "-",
    val dinas: String? = "-",
)
