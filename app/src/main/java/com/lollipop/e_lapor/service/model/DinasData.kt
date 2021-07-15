package com.lollipop.e_lapor.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DinasData(
    val code: Int?=0,
    val message: String?="-",
    val data: List<DinasResult>?=null,
    val status: String?="-"
)
