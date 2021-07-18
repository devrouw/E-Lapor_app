package com.lollipop.e_lapor.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AduanData(
    val code: Int?=0,
    val message: String?="-",
    val data: List<AduanResult>?=null,
    val status: String?="-"
)
