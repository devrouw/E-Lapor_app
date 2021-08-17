package com.lollipop.e_lapor.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DinasResult(
    val id: String? = "-",
    val username: String? = "-",
    val password: String? = "-",
    val dinas: String? = "-",
    val dp_dinas: String? = "-",
)
