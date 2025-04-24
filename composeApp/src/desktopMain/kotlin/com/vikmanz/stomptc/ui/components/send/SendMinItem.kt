package com.vikmanz.stomptc.ui.components.send

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vikmanz.stomptc.model.SendModel
import com.vikmanz.stomptc.ui.components.common.CustomButton
import com.vikmanz.stomptc.ui.components.common.CustomIconButton

@Preview
@Composable
fun MessageMinItemPreview() {
    MaterialTheme {
        MessageMinItem()
    }
}

@Composable
fun MessageMinItem(
    message: SendModel = SendModel(),
    onUpdate: (SendModel) -> Unit = {},
    onSend: () -> Unit = {},
) {
    Card(
            shape = MaterialTheme.shapes.small,
            border = BorderStroke(
                    1.dp,
                    Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
    ) {
        Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(
                    message.name,
                    style = MaterialTheme.typography.h5,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                CustomIconButton(
                        onClick = {
                            if (message.isMaximize) {
                                onUpdate(message.copy(isMaximize = false))
                            } else {
                                onUpdate(message.copy(isMaximize = true))
                            }
                        },
                        icon = Icons.Default.Edit
                )

                CustomIconButton(
                    onClick = onSend,
                    icon =  Icons.AutoMirrored.Filled.Send,
                )

            }
        }

    }
}