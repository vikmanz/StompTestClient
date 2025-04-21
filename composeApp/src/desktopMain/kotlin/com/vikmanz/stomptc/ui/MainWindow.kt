package com.vikmanz.stomptc.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sun.org.apache.xml.internal.serializer.utils.Utils.messages
import com.vikmanz.stomptc.service.StorageService
import com.vikmanz.stomptc.ui.components.connection.ConnectionPanel
import com.vikmanz.stomptc.ui.components.message_income.IncomingMessagesBlock
import com.vikmanz.stomptc.ui.components.message_outcome.MessagesSendPanel
import com.vikmanz.stomptc.ui.components.subscribe.SubscriptionBlock
import com.vikmanz.stomptc.ui.vm.ConnectionViewModel
import com.vikmanz.stomptc.ui.vm.MessagesViewModel

@Preview
@Composable
fun MainWindowPreview() {
    MaterialTheme {
        MainWindow()
    }
}
@Composable
fun MainWindow(
    connectionViewModel: ConnectionViewModel = ConnectionViewModel(),
    messagesViewModel: MessagesViewModel = MessagesViewModel(),
) {

    val subs = connectionViewModel.subs.collectAsState()
    val incomingMessages = messagesViewModel.incomeMessages.collectAsState()

    Row {
        Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.33f)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
        ) {
            ConnectionPanel(
                    connectionViewModel = connectionViewModel,
                    onSave = {
                        connectionViewModel.saveToStorage(messagesViewModel.outcomeMessages.value)
                    },
                    onLoad = {
                        connectionViewModel.loadFromStorage()?.let {
                            messagesViewModel.loadFromStorage(it.sends)
                        }
                    }
            )

            SubscriptionBlock(
                    subscriptions = subs.value,
                    onAddSubscription = connectionViewModel::addEmptySubscribe,
                    onSubscribeChange = connectionViewModel::onSubscribeChange,
                    onSubscribe = connectionViewModel::subscribe,
                    onUnsubscribe = connectionViewModel::unSubscribe,
                    onDeleteSubscribe = connectionViewModel::removeSubscribe,
            )

            MessagesSendPanel(
                    messagesViewModel = messagesViewModel
            )

        }
        Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
        ) {
            IncomingMessagesBlock(
                    messages = incomingMessages.value,
                    onClear = messagesViewModel::clearIncomingMessages,
                    modifier = Modifier.fillMaxSize()
            )
        }
    }

}

