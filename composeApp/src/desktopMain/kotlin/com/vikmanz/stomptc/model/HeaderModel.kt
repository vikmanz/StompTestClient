package com.vikmanz.stomptc.model

import kotlinx.serialization.Serializable

@Serializable
data class HeaderModel(
    val key: String = "",
    val value: String = ""
)
