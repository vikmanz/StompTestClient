package com.vikmanz.stomptc.ui.components.connection

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vikmanz.stomptc.model.ConnectionStatus
import com.vikmanz.stomptc.ui.components.common.CircleAndText
import com.vikmanz.stomptc.ui.components.common.CollapsibleCard
import com.vikmanz.stomptc.ui.components.headers.HeadersBlock
import com.vikmanz.stomptc.ui.vm.ConnectionViewModel

@Preview
@Composable
fun ConnectionPanelPreview() {
    MaterialTheme {
        ConnectionPanel()
    }
}

@Composable
fun ConnectionPanel(
    connectionViewModel: ConnectionViewModel = ConnectionViewModel(),
    onSave: () -> Unit = {},
    onLoad: () -> Unit = {}
) {
    val connectionConfig by connectionViewModel.connectionConfig.collectAsState()
    val connectionStatus by connectionViewModel.connectionStatus.collectAsState()
    var expanded by remember { mutableStateOf(true) }

    CollapsibleCard(
        title = "Connection Settings",
        expanded = expanded,
        onExpandChange = { expanded = it },
        headerContent = {
            if (connectionConfig.isConnected) {
                CircleAndText(connectionStatus)
            }
        }
    ) {

            OutlinedTextField(
                    value = connectionConfig.endpoint,
                    onValueChange = { connectionViewModel.updateEndpoint(it) },
                    label = { Text("WebSocket Endpoint") },
                    modifier = Modifier.fillMaxWidth()
            )

            HeadersBlock(
                    headers = connectionConfig.headers,
                    onAdd =  connectionViewModel::addHeader,
                    onChange = connectionViewModel::updateHeader,
                    onRemove = connectionViewModel::removeHeader
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                if (!ConnectionStatus.isValidStatus(connectionStatus)) {
                    Text(
                        connectionStatus,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onLoad
                        ) {
                            Text("Load")
                        }

                        Button(
                            onClick = onSave
                        ) {
                            Text("Save")
                        }
                    }

                    if (ConnectionStatus.isValidStatus(connectionStatus)) {
                        Text(
                            connectionStatus,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                        )
                    }

                    Button(
                        onClick = {
                            if (connectionConfig.isConnected) {
                                connectionViewModel.disconnect()
                            } else {
                                connectionViewModel.connect()
                                expanded = !expanded
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (connectionConfig.isConnected) {
                                MaterialTheme.colors.error
                            } else {
                                MaterialTheme.colors.primary
                            }
                        ),
                    ) {
                        Text(if (connectionConfig.isConnected) "Disconnect" else "Connect")
                    }

                }
            }
        }
}