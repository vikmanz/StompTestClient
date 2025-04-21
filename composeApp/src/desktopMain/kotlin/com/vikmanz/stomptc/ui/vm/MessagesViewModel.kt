package com.vikmanz.stomptc.ui.vm

import StompService
import StorageService
import com.vikmanz.stomptc.model.ConnectionModel
import com.vikmanz.stomptc.model.StompFrame
import com.vikmanz.stomptc.model.StompMessageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MessagesViewModel {

    val messagesMock = listOf(
            StompMessageModel("/app/user/4", "{\n" +
                                             "  \"type\": \"QUEUE_ADD\"\n" +
                                             "}"),
            StompMessageModel("/app/user/4", "{\n" +
                                             "  \"type\": \"QUEUE_REMOVE\"\n" +
                                             "}")
//            StompMessageModel()
    )

    private val _outcomeMessages = MutableStateFlow<List<StompMessageModel>>(
//            emptyList()
            messagesMock
    )
    val outcomeMessages: StateFlow<List<StompMessageModel>> = _outcomeMessages
    val incomeMessages: StateFlow<List<StompFrame>> = StompService.receivedMessages

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        loadFromStorage()
    }

    fun clearIncomingMessages() {
        StompService.clearIncomingMessages()
    }

    fun addMessage() {
        _outcomeMessages.value += StompMessageModel()
    }

    fun removeMessage(index: Int) {
        _outcomeMessages.value = _outcomeMessages.value.toMutableList().apply { removeAt(index) }
    }

    fun updateMessage(index: Int, message: StompMessageModel) {
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

    fun loadFromStorage() {
//        val (_, messages) = StorageService.load()
//        _messages.value = messages
    }

    fun saveToStorage(connectionConfig: ConnectionModel) {
        StorageService.save(connectionConfig, _outcomeMessages.value)
    }
}