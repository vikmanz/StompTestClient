package com.vikmanz.stomptc.ui.components.send

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
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
import kotlin.math.abs

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

    val scrollState = rememberScrollState()
    var isScrollable by remember { mutableStateOf(true) }
    var lastSize by remember { mutableStateOf(0) }
    val currentSize = messages.value.size

    LaunchedEffect(currentSize) {
        if (currentSize != 0 && abs(currentSize - lastSize) == 1 && isScrollable) {
            scrollState.scrollTo(messages.value.lastIndex)
            lastSize = messages.value.size
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier.verticalScroll(scrollState).weight(1f).padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                if (messages.value.isEmpty()) {
                    Text(
                        "No messages configured",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }


                messages.value.forEachIndexed { index, m ->
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

                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            val lastMessage = messages.value.getOrNull(messages.value.lastIndex)
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

            VerticalScrollbar(
                adapter = rememberScrollbarAdapter(scrollState),
                modifier = Modifier
                    .width(8.dp)
                    .fillMaxHeight()
            )
        }

    }
}