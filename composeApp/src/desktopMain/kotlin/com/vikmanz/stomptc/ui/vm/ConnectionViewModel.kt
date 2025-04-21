package com.vikmanz.stomptc.ui.vm

import StompService
import StorageService
import com.vikmanz.stomptc.model.ConnectionModel
import com.vikmanz.stomptc.model.HeaderModel
import com.vikmanz.stomptc.model.StompMessageModel
import com.vikmanz.stomptc.model.SubscriptionModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConnectionViewModel {
    private val _connectionConfig = MutableStateFlow(ConnectionModel())
    val connectionConfig: StateFlow<ConnectionModel> = _connectionConfig

    private val _connectionStatus = MutableStateFlow("Disconnected")
    val connectionStatus: StateFlow<String> = _connectionStatus

    private val _subs = MutableStateFlow(listOf(SubscriptionModel("/topic/user/4")))
    val subs: StateFlow<List<SubscriptionModel>> = _subs

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun updateEndpoint(endpoint: String) {
        _connectionConfig.value = _connectionConfig.value.copy(endpoint = endpoint)
    }

    fun addHeader(header: HeaderModel) {
        val newHeaders = _connectionConfig.value.headers.toMutableList()
        newHeaders.add(header)
        _connectionConfig.value = _connectionConfig.value.copy(headers = newHeaders)
    }

    fun removeHeader(header: HeaderModel) {
        val newHeaders = _connectionConfig.value.headers.toMutableList()
        newHeaders.remove(header)
        _connectionConfig.value = _connectionConfig.value.copy(headers = newHeaders)
    }

    fun subscribe(sub: SubscriptionModel) {
        if (!sub.isSubscribed && sub.topic.isNotBlank()) {
            coroutineScope.launch {
                println("subscribe! $sub")
                StompService.subscribe(sub.topic)
                val newSub = sub.copy(isSubscribed = true)
                if (!_subs.value.contains(sub)) {
                    _subs.value += newSub
                } else {
                    onSubscribeChange(sub.copy(isSubscribed = true))
                }
            }
        }
    }

    fun unSubscribe(sub: SubscriptionModel) {
        coroutineScope.launch {
            if (sub.isSubscribed) {
                StompService.unsubscribe(sub.topic)
            }
            onSubscribeChange(sub.copy(isSubscribed = false))
        }
    }

    fun removeSubscribe(sub: SubscriptionModel) {
        coroutineScope.launch {
            if (sub.isSubscribed) {
                StompService.unsubscribe(sub.topic)
            }
            removeSubFromList(sub)
        }
    }
    fun addEmptySubscribe() {
        _subs.value += SubscriptionModel(topic = "")
    }

    fun onSubscribeChange(sub: SubscriptionModel) {
        _subs.value = _subs.value.map {
            if (it.id == sub.id) sub else it
        }
    }

    private fun removeSubFromList(sub: SubscriptionModel) {
        _subs.value = _subs.value.toMutableList().apply {
            this.remove(sub)
        }
    }

    fun connect() {
        coroutineScope.launch {
            try {
                _connectionStatus.value = "Connecting..."
                StompService.connect(_connectionConfig.value)
                _connectionStatus.value = "Connected"
            } catch (e: Exception) {
                _connectionStatus.value = "Error: ${e.message}"
            }
        }
    }

    fun disconnect() {
        coroutineScope.launch {
            try {
                _connectionStatus.value = "Disconnecting..."
                StompService.disconnect()
                _subs.value = _subs.value.map {
                    it.copy(isSubscribed = false)
                }
                _connectionStatus.value = "Disconnected"
            } catch (e: Exception) {
                _connectionStatus.value = "Error: ${e.message}"
            }
        }
    }

    fun loadFromStorage() {
        val (config, _) = StorageService.load()
        _connectionConfig.value = config
    }

    fun saveToStorage(messages: List<StompMessageModel>) {
        StorageService.save(_connectionConfig.value, messages)
    }
}