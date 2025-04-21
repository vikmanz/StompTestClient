package com.vikmanz.stomptc.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import com.vikmanz.stomptc.model.ConnectionModel

@Preview
@Composable
fun ConnectionConfigUIPreview() {
    MaterialTheme {
        ConnectionConfigUI()
    }
}

@Composable
@Preview
fun ConnectionConfigUI(
    config: ConnectionModel = ConnectionModel(),
    onChange: (ConnectionModel) -> Unit = {}
) {

//    Column {
//        OutlinedTextField(
//            value = config.endpoint,
//            onValueChange = {
//                onChange(config.copy(endpoint = it))
//            },
//            label = { Text("WebSocket endpoint") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(Modifier.height(8.dp))
//        OutlinedTextField(
//            value = headersText,
//            onValueChange = {
//                headersText = it
//                runCatching {
//                    val parsed = kotlinx.serialization.json.Json.decodeFromString<Map<String, String>>(it)
//                    onChange(config.copy(headers = parsed))
//                }
//            },
//            label = { Text("Headers (JSON формат)") },
//            modifier = Modifier.fillMaxWidth()
//        )
//    }
}

