package com.vikmanz.stomptc.service

import com.vikmanz.stomptc.model.ConnectionModel
import com.vikmanz.stomptc.model.SendModel
import com.vikmanz.stomptc.model.SubscriptionModel
import com.vikmanz.stomptc.utils.FileDialogUtil
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object StorageService {
    private val json = Json { prettyPrint = true }

    fun saveWithDialog(config: ConnectionModel, subscriptions: List<SubscriptionModel>, messages: List<SendModel>) {
        val file = FileDialogUtil.showSaveDialog() ?: return
        val wrapper = StorageData(config, subscriptions, messages)
        file.writeText(json.encodeToString(wrapper))
    }

    fun loadWithDialog(): StorageData? {
        val file = FileDialogUtil.showLoadDialog() ?: return null
        return try {
            val wrapper = json.decodeFromString<StorageData>(file.readText())
            wrapper.let {
                it.copy(
                    config = it.config.copy(isConnected = false),
                    subscriptions = it.subscriptions.map { it.copy(isSubscribed = false) }
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}


@kotlinx.serialization.Serializable
data class StorageData(
    val config: ConnectionModel,
    val subscriptions: List<SubscriptionModel>,
    val sends: List<SendModel>
)
