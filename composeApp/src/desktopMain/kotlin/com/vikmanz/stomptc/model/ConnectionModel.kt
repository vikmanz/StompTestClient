package com.vikmanz.stomptc.model

import kotlinx.serialization.Serializable

@Serializable
data class ConnectionModel(
    val endpoint: String = "ws://localhost:8080/",
    val headers: List<HeaderModel> = emptyList(),
    val isConnected: Boolean = false
)