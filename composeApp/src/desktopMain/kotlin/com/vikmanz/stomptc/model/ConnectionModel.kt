package com.vikmanz.stomptc.model

import kotlinx.serialization.Serializable

@Serializable
data class ConnectionModel(
    val endpoint: String = "ws://localhost:8080/",
    val headers: List<HeaderModel> = emptyList(),
    val isConnected: Boolean = false
)

enum class ConnectionStatus(val label: String) {
    CONNECTED("Connected"),
    CONNECTING("Connecting..."),
    DISCONNECTING("Disconnecting..."),
    DISCONNECTED("Disconnected");

    companion object {
        fun isValidStatus(value: String): Boolean =
            values().any { it.label == value }

        fun isValidStatusIgnoreCase(value: String): Boolean =
            values().any { it.label.equals(value, ignoreCase = true) }
    }
}