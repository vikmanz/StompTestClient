package com.vikmanz.stomptc.ui.components.income

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import java.awt.SystemColor.text
import javax.swing.Scrollable

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

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = listState
        ) {
            itemsIndexed(messages) { _, frame ->
                StompFrameItem(frame)
            }
        }
    }

}