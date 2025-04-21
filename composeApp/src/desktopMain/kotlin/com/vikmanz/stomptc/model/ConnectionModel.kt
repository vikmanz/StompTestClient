package com.vikmanz.stomptc.model

import kotlinx.serialization.Serializable

@Serializable
data class ConnectionModel(
    val endpoint: String = "ws://localhost:8080/api/v1/ws",
    val headers: List<HeaderModel> = listOf(
            HeaderModel(
                    "Authorization",
                    "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ1c2VyTGV2ZWwxYSIsImV4cCI6MTc0NTI1ODU0NH0.OM1s5hwpRtJLXJercxenb5MeEgr_WZD2xab4zJizfanxwArN68xLpbA_t3BUT9l3"
            )
    ),
    val isConnected: Boolean = false
)