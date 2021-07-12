package com.lollipop.e_lapor.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KirimData(
    val code: Int?=0,
    val message: String?="-",
    val data: String?="",
    val status: String?="-"
)