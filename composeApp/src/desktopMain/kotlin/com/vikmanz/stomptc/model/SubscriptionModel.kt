package com.vikmanz.stomptc.model

import kotlinx.serialization.Serializable
import java.util.UUID


@Serializable
data class SubscriptionModel(
    val topic: String = "/app/user/4",
    val isSubscribed: Boolean = false,
    val id: String = UUID
        .randomUUID().toString(),
)