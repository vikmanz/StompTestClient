package com.vikmanz.stomptc.ui.components.income

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vikmanz.stomptc.model.StompFrame
import com.vikmanz.stomptc.ui.components.common.CustomButton
import com.vikmanz.stomptc.ui.components.common.CustomCheckbox
import com.vikmanz.stomptc.ui.theme.COLOR_Card_header_bg

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

    val listState = rememberLazyListState()
    var isScrollable by remember { mutableStateOf(true) }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty() && isScrollable) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(COLOR_Card_header_bg).fillMaxWidth().padding(8.dp)
        ) {
            Text(
                "Incoming messages",
                style = MaterialTheme.typography.h4
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                CustomCheckbox(
                    checked = isScrollable,
                    onCheckChange = { isScrollable = !isScrollable },
                    text = "Autoscroll"
                )

                CustomButton(
                    text = "Clear",
                    onClick = onClear,
                )
            }

        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(messages) { _, frame ->
                    StompFrameItem(frame)
                }
            }

            VerticalScrollbar(
                adapter = rememberScrollbarAdapter(listState),
                modifier = Modifier
                    .width(16.dp)
                    .fillMaxHeight()
            )
        }
    }

}