package com.lollipop.e_lapor.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KategoriResult(
    val id: String? = "-",
    val nama_kategori: String? = "-",
)
