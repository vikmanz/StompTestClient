package com.vikmanz.stomptc.ui.components.subscribe

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
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
fun SubscribeReadLinePreview() {
    MaterialTheme {
        SubscribeReadLine()
    }
}

@Composable
fun SubscribeReadLine(
    sub: SubscriptionModel = SubscriptionModel(),
    onUnSubscribe: (SubscriptionModel) -> Unit = {},
    onDelete: (SubscriptionModel) -> Unit = {}
) {

    Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
    ) {

        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .border(
                            1.dp,
                            MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
                    )
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                    Icons.Filled.Done,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
            )
            Spacer(Modifier.width(8.dp))
            Text(
                    sub.topic,
                    modifier = Modifier.weight(1f)
            )

            Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                CustomIconButton(
                        onClick = {
                            onUnSubscribe(sub)
                        },
                        icon = Icons.Default.Edit,
                        color = COLOR_Green
                )

//                CustomIconButton(
//                        onClick = {
//                            onDelete(sub)
//                        },
//                        icon = Icons.Default.Delete,
//                        color = COLOR_Red
//                )
            }

        }
    }
}