package com.vikmanz.stomptc.ui.components.headers

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.vikmanz.stomptc.model.HeaderModel
import com.vikmanz.stomptc.ui.components.common.CollapsibleFlatCard
import com.vikmanz.stomptc.ui.theme.COLOR_Red

@Preview
@Composable
fun HeadersBlockPreview() {
    MaterialTheme {
        HeadersBlock()
    }
}

@Composable
fun HeadersBlock(
    headers: List<HeaderModel> = emptyList(),
    onAdd: (HeaderModel) -> Unit = {},
    onChange: (key: Int, header: HeaderModel) -> Unit = { _, _ -> },
    onRemove: (HeaderModel) -> Unit = {},
) {

    CollapsibleFlatCard(
        title = "Headers"
    ) {

        headers.forEachIndexed { index, header ->
            HeaderLine(
                key = header.key,
                value = header.value,
                onKeyChange = {
                        onChange(
                                index,
                                header.copy(key = it)
                        )
                    },
                onValueChange = {
                        onChange(
                                index,
                                header.copy(value = it)
                        )
                    },
                icon = Icons.Default.Delete,
                iconColor = COLOR_Red,
                iconTint = Color.White,
                onButtonClick = { onRemove(header) }
            )
        }

        Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                    onClick = {
                        val lastHeader = headers.getOrNull(headers.lastIndex)
                            onAdd(
                                HeaderModel(
                                        lastHeader?.key ?: "",
                                        lastHeader?.value ?: "",
                                )
                        )
                    }
            ) {
                Text("Add Header")
            }
        }

    }

}