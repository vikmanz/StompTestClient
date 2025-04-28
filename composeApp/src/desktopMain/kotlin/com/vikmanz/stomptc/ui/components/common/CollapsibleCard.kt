package com.vikmanz.stomptc.ui.components.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vikmanz.stomptc.ui.theme.COLOR_Card_bg
import com.vikmanz.stomptc.ui.theme.COLOR_Card_header_bg

@Composable
fun CollapsibleCard(
    title: String,
    modifier: Modifier = Modifier,
    expanded: Boolean? = null,
    onExpandChange: ((Boolean) -> Unit)? = null,
    initialExpanded: Boolean = true,
    backgroundColor: Color = COLOR_Card_bg,
    headerBackgroundColor: Color? = COLOR_Card_header_bg,
    border: BorderStroke? = BorderStroke(1.dp, Color.Black),
    elevation: Int = 4,
    headerContent: @Composable RowScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    var internalExpanded by remember { mutableStateOf(initialExpanded) }
    val isExpanded = expanded ?: internalExpanded
    val onExpandToggle = onExpandChange ?: { internalExpanded = !internalExpanded }

    Card(
        backgroundColor = backgroundColor,
        border = border,
        elevation = elevation.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerBackgroundColor ?: Color.Transparent)
                    .padding(8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    headerContent()
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { onExpandToggle(!isExpanded) }
                    )
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(8.dp)
                ) {
                    content()
                }
            }
        }
    }
}