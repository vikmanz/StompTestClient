package com.vikmanz.stomptc.ui.components.send

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vikmanz.stomptc.model.SendModel
import com.vikmanz.stomptc.ui.components.common.CollapsibleCard
import com.vikmanz.stomptc.ui.components.common.CustomCheckbox
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
    messagesViewModel: MessagesViewModel = MessagesViewModel()
) {

    val messages = messagesViewModel.outcomeMessages.collectAsStateWithLifecycle()

    val listState = rememberLazyListState()
    var isScrollable by remember { mutableStateOf(true) }

    LaunchedEffect(messages.value.size) {
        if (messages.value.isNotEmpty() && isScrollable) {
            listState.animateScrollToItem(messages.value.lastIndex)
        }
    }

    CollapsibleCard(
        title = "OutComing messages",
        headerContent = {
            CustomCheckbox(
                checked = isScrollable,
                onCheckChange = { isScrollable = !isScrollable },
                text = "Autoscroll"
            )
        }
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = listState
        ) {

            noMessagesInfo(messages.value)

            itemsIndexed(messages.value) { index, m ->
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

            addNewMessage(
                messages.value,
                messagesViewModel
            )


        }

    }
}

private fun LazyListScope.noMessagesInfo(
    messages: List<SendModel>
) {
    if (messages.isEmpty()) {
        item {
            Text(
                "No messages configured",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

private fun LazyListScope.addNewMessage(
    messages: List<SendModel>,
    messagesViewModel: MessagesViewModel
) {
    item {
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    val lastMessage = messages.getOrNull(messages.lastIndex)
                    messagesViewModel.addMessage(
                        SendModel(
                            topic = lastMessage?.topic
                                ?: "",
                        )
                    )
                }
            ) {
                Text("Add Message")
            }
        }
    }
}