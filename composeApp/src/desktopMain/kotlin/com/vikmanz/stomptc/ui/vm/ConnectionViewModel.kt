package com.vikmanz.stomptc.ui.vm

import StompService
import com.vikmanz.stomptc.service.StorageService
import com.vikmanz.stomptc.model.ConnectionModel
import com.vikmanz.stomptc.model.ConnectionStatus
import com.vikmanz.stomptc.model.HeaderModel
import com.vikmanz.stomptc.model.SendModel
import com.vikmanz.stomptc.model.SubscriptionModel
import com.vikmanz.stomptc.service.StorageData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConnectionViewModel {
    private val _connectionConfig = MutableStateFlow(ConnectionModel())
    val connectionConfig: StateFlow<ConnectionModel> = _connectionConfig

    private val _connectionStatus = MutableStateFlow(ConnectionStatus.DISCONNECTED.label)
    val connectionStatus: StateFlow<String> = _connectionStatus

    private val _subs = MutableStateFlow(listOf(SubscriptionModel("/topic/user/")))
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

    fun updateHeader(index:Int, header: HeaderModel) {
        val newHeaders = _connectionConfig.value.headers.toMutableList()
        newHeaders[index] = header
        _connectionConfig.value = _connectionConfig.value.copy(headers = newHeaders)
    }

    fun subscribe(sub: SubscriptionModel) {
        if (
            _connectionConfig.value.isConnected &&
            !sub.isSubscribed && sub.topic.isNotBlank()) {
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
        } else {
            println("Connection ${if (_connectionConfig.value.isConnected) "connected." else "NOT connected."}")
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
                _connectionStatus.value = ConnectionStatus.CONNECTING.label
                StompService.connect(_connectionConfig.value)
                _connectionConfig.update { it.copy(isConnected = true) }

                _connectionStatus.value = ConnectionStatus.CONNECTED.label
            } catch (e: Exception) {
                _connectionStatus.value = "Error: ${e.message}"
            }
        }
    }

    fun disconnect() {
        coroutineScope.launch {
            try {
                _connectionStatus.value = ConnectionStatus.DISCONNECTING.label
                StompService.disconnect()
                _subs.value = _subs.value.map {
                    it.copy(isSubscribed = false)
                }
                _connectionConfig.update { it.copy(isConnected = false) }
                _connectionStatus.value = ConnectionStatus.DISCONNECTED.label
            } catch (e: Exception) {
                _connectionStatus.value = "Error: ${e.message}"
            }
        }
    }

    fun saveToStorage(messages: List<SendModel>) {
        StorageService.saveWithDialog(
                _connectionConfig.value,
                _subs.value.map { it.copy(isSubscribed = false)},
                messages
        )
    }

    fun loadFromStorage(): StorageData? {
        val storageData = StorageService.loadWithDialog()
        storageData?.let {
            _connectionConfig.value = storageData.config
            _subs.value = storageData.subscriptions
        }
        return storageData
    }

}