package com.vikmanz.stomptc.ui.components.message_outcome

import com.vikmanz.stomptc.ui.components.headers.HeadersBlock
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vikmanz.stomptc.model.StompMessageModel
import com.vikmanz.stomptc.ui.components.common.CustomButton
import com.vikmanz.stomptc.ui.components.common.CustomIconButton

@Preview
@Composable
fun MessageMaxItemPreview() {
    MaterialTheme {
        MessageMaxItem()
    }
}

@Composable
fun MessageMaxItem(
    message: StompMessageModel = StompMessageModel(),
    onUpdate: (StompMessageModel) -> Unit = {},
    onSend: () -> Unit = {},
    onRemove: () -> Unit = {}
) {

    Card(
            shape = MaterialTheme.shapes.small,
            border = BorderStroke(
                    1.dp,
                    Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
    ) {
        Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(8.dp)
        ) {
            Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                        "Message",
                        style = MaterialTheme.typography.subtitle1
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    CustomIconButton(
                            onClick = {
                                onUpdate(message.copy(isMaximize = false))
                            },
                            icon = Icons.Default.Done
                    )
                    CustomIconButton(
                            onClick = onRemove,
                            icon = Icons.Default.Close
                    )
                }
            }

            OutlinedTextField(
                    value = message.topic,
                    onValueChange = {
                        onUpdate(message.copy(topic = it))
                    },
                    label = { Text("Topic/Destination") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true

            )

            Text(
                    "Content",
                    style = MaterialTheme.typography.subtitle1
            )

            OutlinedTextField(
                    value = message.message,
                    onValueChange = {
                        onUpdate(message.copy(message = it))
                    },
                    label = { Text("Message Content") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    maxLines = 10
            )

            HeadersBlock(
                    headers = message.headers,
                    onAdd = { newHeader ->
                        onUpdate(
                                message.copy(
                                        headers = message.headers
                                            .toMutableList()
                                            .apply {
                                                this.add(newHeader)
                                            }
                                ))
                    },
                    onRemove = { removed ->
                        onUpdate(
                                message.copy(
                                        headers = message.headers
                                            .toMutableList()
                                            .apply {
                                                this.remove(removed)
                                            }
                                ))
                    },
            )

            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.weight(1f),
                ) {
                    Text(
                            text = "Name:",
                    )
                    OutlinedTextField(
                            value = message.name,
                            onValueChange = {
                                onUpdate(message.copy(name = it))
                            },
                            label = { Text("Mame") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                    )
                }

                Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    CustomIconButton(
                            onClick = {
                                onUpdate(message.copy(isMaximize = false))
                            },
                            icon = Icons.Default.Done
                    )
                    CustomButton(
                            text = "Send",
                            onClick = onSend,
                    )
                }
            }
        }
    }
}