package com.vikmanz.stomptc.model

import kotlinx.serialization.Serializable

@Serializable
data class StompMessageModel(
    val topic: String = "/app/example",
    val message: String = "{}",
    val headers: List<HeaderModel> = emptyList(),
    val name: String = "",
    val isMaximize: Boolean = true
)
