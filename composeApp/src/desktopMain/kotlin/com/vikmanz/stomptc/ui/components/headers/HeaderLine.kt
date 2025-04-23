package com.vikmanz.stomptc.ui.components.headers

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.vikmanz.stomptc.ui.components.common.CustomIconButton
import androidx.compose.ui.graphics.Color

@Preview
@Composable
fun HeaderLinePreview() {
    MaterialTheme {
        HeaderLine()
    }
}

@Composable
fun HeaderLine(
    key: String = "",
    value: String = "",
    onKeyChange: (String) -> Unit = {},
    onValueChange: (String) -> Unit = {},
    icon: ImageVector = Icons.Default.Close,
    iconColor: Color = Color.Transparent,
    onButtonClick: () -> Unit = {},
) {
    Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        OutlinedTextField(
                value = key,
                onValueChange = onKeyChange,
                label = { Text("Key") },
                modifier = Modifier.weight(1f),
                singleLine = true
        )

        OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text("Value") },
                modifier = Modifier.weight(1f),
                singleLine = true
        )

        CustomIconButton(
                onClick = onButtonClick,
                icon = icon,
                color = iconColor
        )
    }
}