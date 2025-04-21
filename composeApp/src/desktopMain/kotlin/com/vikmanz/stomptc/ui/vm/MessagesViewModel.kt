package com.vikmanz.stomptc.ui.vm

import StompService
import com.vikmanz.stomptc.service.StorageService
import com.vikmanz.stomptc.model.StompFrame
import com.vikmanz.stomptc.model.SendModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MessagesViewModel {

    private val _outcomeMessages = MutableStateFlow<List<SendModel>>(emptyList())
    val outcomeMessages: StateFlow<List<SendModel>> = _outcomeMessages

    val incomeMessages: StateFlow<List<StompFrame>> = StompService.receivedMessages

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        loadFromStorage()
    }

    fun clearIncomingMessages() {
        StompService.clearIncomingMessages()
    }

    fun addMessage(sendModel: SendModel) {
        _outcomeMessages.value += sendModel
    }

    fun removeMessage(index: Int) {
        _outcomeMessages.value = _outcomeMessages.value.toMutableList().apply { removeAt(index) }
    }

    fun updateMessage(index: Int, message: SendModel) {
        _outcomeMessages.value = _outcomeMessages.value.toMutableList().apply {
            this[index] = message
        }
    }

    fun sendMessage(index: Int) {
        val message = _outcomeMessages.value.getOrNull(index) ?: return
        coroutineScope.launch {
            try {
                StompService.send(message)
            } catch (e: Exception) {
                println("Error sending message: ${e.message}")
            }
        }
    }

    private fun loadFromStorage() {
        val storageData = StorageService.load()
        _outcomeMessages.value = storageData.sends
    }

}