package com.vikmanz.stomptc.ui.components.subscribe

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vikmanz.stomptc.model.SubscriptionModel
import com.vikmanz.stomptc.ui.components.common.CustomButton

@Preview
@Composable
fun SubscriptionBlockPreview() {
    MaterialTheme {
        SubscriptionBlock(
                subscriptions = listOf(SubscriptionModel(""))
        )
    }
}

@Composable
fun SubscriptionBlock(
    subscriptions: List<SubscriptionModel> = emptyList(),
    onAddSubscription: () -> Unit = {},
    onSubscribeChange: (SubscriptionModel) -> Unit = {},
    onSubscribe: (SubscriptionModel) -> Unit = {},
    onUnsubscribe: (SubscriptionModel) -> Unit = {},
    onDeleteSubscribe: (SubscriptionModel) -> Unit = {}
) {

    Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
    ) {
        Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                    "Subscribes",
                    style = MaterialTheme.typography.subtitle1
            )

            subscriptions.forEach { sub ->
                if (sub.isSubscribed) {
                    SubscribeReadLine(
                            sub = sub,
                            onUnSubscribe = onUnsubscribe,
                            onDelete = onDeleteSubscribe,
                    )
                } else {
                    SubscribeEditedLine(
                            sub = sub,
                            onChange = onSubscribeChange,
                            onSubscribe = onSubscribe,
                            onDelete = onDeleteSubscribe
                    )
                }
            }
            CustomButton(
                    text = "Add subscribe",
                    onClick = onAddSubscription,
                    modifier = Modifier.align(Alignment.End)
            )

        }
    }
}