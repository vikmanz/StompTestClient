//package com.vikmanz.stomptc.ui
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.Button
//import androidx.compose.material.Card
//import androidx.compose.material.OutlinedTextField
//import androidx.compose.material.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.vikmanz.stomptc.model.ConnectionConfig
//import com.vikmanz.stomptc.model.StompMessageModel
//
//@Composable
//fun ConnectionSettings(config: ConnectionConfig, onUpdate: (ConnectionConfig) -> Unit) {
//    Column {
//        OutlinedTextField(
//            value = config.endpoint,
//            onValueChange = {
//                onUpdate(config.copy(endpoint = it))
//            },
//            label = { Text("Endpoint") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        var headerString by remember {
//            mutableStateOf(config.headers.entries.joinToString("\n") { "${it.key}:${it.value}" })
//        }
//        OutlinedTextField(
//            value = headerString,
//            onValueChange = {
//                headerString = it
//                val headers = it.lines()
//                    .mapNotNull { line ->
//                        val (k, v) = line.split(":", limit = 2).map { it.trim() }
//                        if (k.isNotBlank() && v.isNotBlank()) k to v else null
//                    }.toMap()
//                onUpdate(config.copy(headers = headers))
//            },
//            label = { Text("Headers (key:value) per line") },
//            modifier = Modifier.fillMaxWidth().height(100.dp)
//        )
//    }
//}
//
//@Composable
//fun MessageBlock(msg: StompMessageModel, onSend: (StompMessageModel) -> Unit) {
//    Card(modifier = Modifier.padding(8.dp).fillMaxWidth(), elevation = 4.dp) {
//        Column(modifier = Modifier.padding(8.dp)) {
//            OutlinedTextField(
//                value = msg.destination,
//                onValueChange = { msg.destination = it },
//                label = { Text("Destination") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            OutlinedTextField(
//                value = msg.body,
//                onValueChange = { msg.body = it },
//                label = { Text("Body") },
//                modifier = Modifier.fillMaxWidth().height(100.dp)
//            )
//            OutlinedTextField(
//                value = msg.headers.entries.joinToString("\n") { "${it.key}:${it.value}" },
//                onValueChange = {
//                    val headers = it.lines()
//                        .mapNotNull { line ->
//                            val parts = line.split(":", limit = 2)
//                            if (parts.size == 2) parts[0].trim() to parts[1].trim() else null
//                        }.toMap()
//                    msg.headers = headers
//                },
//                label = { Text("Headers (key:value) per line") },
//                modifier = Modifier.fillMaxWidth().height(80.dp)
//            )
//            Button(onClick = { onSend(msg) }, modifier = Modifier.align(Alignment.End)) {
//                Text("Send")
//            }
//        }
//    }
//}