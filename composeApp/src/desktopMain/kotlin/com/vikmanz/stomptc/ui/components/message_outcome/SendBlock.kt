package com.vikmanz.stomptc.ui.components.message_outcome

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vikmanz.stomptc.model.SendModel
import com.vikmanz.stomptc.ui.vm.MessagesViewModel

@Preview
@Composable
fun MessagesPanelPreview() {
    MaterialTheme {
        MessagesSendPanel()
    }
}

@Composable
fun MessagesSendPanel(
    messagesViewModel: MessagesViewModel = MessagesViewModel(),

        ) {

    val sendMessages = messagesViewModel.outcomeMessages.collectAsStateWithLifecycle()

    Card(
            modifier = Modifier.fillMaxSize()
    ) {
        Column(
                modifier = Modifier.padding(16.dp)
        ) {

            if (sendMessages.value.isEmpty()) {
                Text(
                        "No messages configured",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                )
            } else {
                Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(4.dp)
                ) {

                    sendMessages.value.forEachIndexed { index, m ->
                        if (m.isMaximize) {
                            MessageMaxItem(
                                    message = m,
                                    onUpdate = { updated ->
                                        messagesViewModel.updateMessage(
                                                index,
                                                updated
                                        )
                                    },
                                    onSend = {
                                        messagesViewModel.sendMessage(index)
                                    },
                                    onRemove = {
                                        messagesViewModel.removeMessage(index)
                                    }
                            )
                        } else {
                            MessageMinItem(
                                    message = m,
                                    onUpdate = { updated ->
                                        messagesViewModel.updateMessage(
                                                index,
                                                updated
                                        )
                                    },
                                    onSend = {
                                        messagesViewModel.sendMessage(index)
                                    }
                            )
                        }

                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                        onClick = {
                            val lastMessage =
                                sendMessages.value.getOrNull(sendMessages.value.lastIndex)
                            messagesViewModel.addMessage(
                                    SendModel(
                                            topic = lastMessage?.topic ?: "",
                                    )
                            )
                        }
                ) {
                    Text("Add Message")
                }
            }

        }
    }
}