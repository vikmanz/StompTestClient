package com.vikmanz.stomptc.ui.components.message_income

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vikmanz.stomptc.model.StompFrame
import com.vikmanz.stomptc.ui.components.common.CustomButton

@Preview
@Composable
fun IncomingMessagesBlockPreview() {
    MaterialTheme {
        IncomingMessagesBlock()
    }
}

@Composable
fun IncomingMessagesBlock(
    messages: List<StompFrame> = emptyList(),
    onClear: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
    ) {
        Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
        ){
            Text(
                    "Incoming messages",
                    style = MaterialTheme.typography.subtitle1
            )
            CustomButton(
                    text = "Clear",
                    onClick = onClear,
            )
        }

        LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(messages) { _, frame ->
                    StompFrameItem(frame)
            }
        }
    }

}