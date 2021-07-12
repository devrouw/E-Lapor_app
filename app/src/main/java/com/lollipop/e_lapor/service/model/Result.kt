package com.lollipop.e_lapor.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    val id: String? = "-",
    val category: String? = "-",
    val gambar: String? = "-",
    val subtitle: String? = "-",
    val judul: String? = "-",
    val deskripsi: String? = "-",
    val sumber: String? = "-"
)
