package com.lollipop.e_lapor.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KategoriData (
    val code: Int?=0,
    val message: String?="-",
    val data: List<KategoriResult>?=null,
    val status: String?="-"
)