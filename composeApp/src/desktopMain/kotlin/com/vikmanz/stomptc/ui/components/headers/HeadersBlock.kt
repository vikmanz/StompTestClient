package com.vikmanz.stomptc.ui.components.headers

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.vikmanz.stomptc.model.HeaderModel

@Preview
@Composable
fun HeadersBlockPreview() {
    MaterialTheme {
        HeadersBlock()
    }
}

@Composable
fun HeadersBlock(
    headers: List<HeaderModel> = listOf(
            HeaderModel("a", "b"),
            HeaderModel("c", "d"),
            HeaderModel("e", "g")
    ),
    onAdd: (HeaderModel) -> Unit = {},
    onRemove: (HeaderModel) -> Unit = {},
) {

    Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {

        Text(
                "Headers",
                style = MaterialTheme.typography.subtitle1
        )

        headers.forEach { header ->
            HeaderLine(
                    key = header.key,
                    value = header.value,
                    onKeyChange = {

                    },
                    onValueChange = {

                    },
                    icon = Icons.Default.Close,
                    onButtonClick = { onRemove(header) }
            )
        }

        var newKey by remember { mutableStateOf("") }
        var newValue by remember { mutableStateOf("") }
        HeaderLine(
                key = newKey,
                value = newValue,
                onKeyChange = { newKey = it },
                onValueChange = { newValue = it },
                icon = Icons.Default.Add,
                onButtonClick = {
                    if (newKey.isNotBlank()) {
                        onAdd(
                                HeaderModel(
                                        newKey,
                                        newValue
                                )
                        )
                        newKey = ""
                        newValue = ""
                    }
                }
        )
    }

}