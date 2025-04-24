package com.vikmanz.stomptc.ui.components.subscribe

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vikmanz.stomptc.model.SubscriptionModel
import com.vikmanz.stomptc.ui.components.common.CustomIconButton
import com.vikmanz.stomptc.ui.theme.COLOR_Green
import com.vikmanz.stomptc.ui.theme.COLOR_Red

@Preview
@Composable
fun SubscribeEditedLinePreview() {
    MaterialTheme {
        SubscribeEditedLine()
    }
}

@Composable
fun SubscribeEditedLine(
    sub: SubscriptionModel = SubscriptionModel(),
    onChange: (SubscriptionModel) -> Unit = {},
    onSubscribe: (SubscriptionModel) -> Unit = {},
    onDelete: (SubscriptionModel) -> Unit = {}
) {

    Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(64.dp)
    ) {

        OutlinedTextField(
                value = sub.topic.also { println("newValue ${sub.topic}") },
                onValueChange = { new ->
                    onChange(sub.copy(topic = new))
                },
                modifier = Modifier.weight(1f)
        )

        Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            CustomIconButton(
                    onClick = {
                        onSubscribe(sub)
                    },
                    icon = Icons.Default.Done
            )

            CustomIconButton(
                    onClick = {
                        onDelete(sub)
                    },
                    icon = Icons.Default.Delete,
                    color = COLOR_Red,
                    iconTint = Color.White
            )
        }

    }

}