package com.vikmanz.stomptc.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.isCtrlPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.vikmanz.stomptc.ui.MainWindow
import com.vikmanz.stomptc.ui.vm.ConnectionViewModel
import com.vikmanz.stomptc.ui.vm.MessagesViewModel
import kotlin.math.sign

fun main() = application {

    var scale by remember { mutableStateOf(0.75f) }

    val connectionViewModel = remember { ConnectionViewModel() }
    val messagesViewModel = remember { MessagesViewModel() }

    Window(
            onCloseRequest = ::exitApplication,
            title = "STOMP Client",
            state = rememberWindowState(
                    size = DpSize(1200.dp, 800.dp)
            )
    ) {
        Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                val scrollDelta = event.changes.firstOrNull()?.scrollDelta?.y ?: 0f
                                val isCtrlPressed = event.keyboardModifiers.isCtrlPressed

                                if (scrollDelta != 0f && isCtrlPressed) {
                                    scale = (scale - scrollDelta.sign * 0.05f).coerceIn(0.1f, 3f)
                                }
                            }
                        }
                    }
        ) {
            MaterialTheme {
                ScalableContainer(scale = scale) {
                    MainWindow(
                            connectionViewModel,
                            messagesViewModel
                    )
                }
            }
        }
    }

}