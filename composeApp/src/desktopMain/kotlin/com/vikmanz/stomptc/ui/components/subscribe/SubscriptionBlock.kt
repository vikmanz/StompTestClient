package com.vikmanz.stomptc.ui.components.subscribe

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vikmanz.stomptc.model.SubscriptionModel
import com.vikmanz.stomptc.ui.components.common.CircleAndText
import com.vikmanz.stomptc.ui.components.common.CollapsibleCard
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

    CollapsibleCard(
        title = "Subscribes",
        headerContent = {
            if (subscriptions.any { it.isSubscribed }) {
                CircleAndText("Subscribed")
            }
        }
    ) {

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

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomButton(
                text = "Add subscribe",
                onClick = onAddSubscription,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}