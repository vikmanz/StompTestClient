package com.vikmanz.stomptc.ui.components.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun CustomButtonPreview() {
    MaterialTheme {
        CustomButton(text = "Test")
    }
}

@Composable
fun CustomButton(
    text: String = "",
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Button(
            onClick = onClick,
            modifier = Modifier.wrapContentHeight().wrapContentWidth().then(modifier)
    ) {
        Text(text)
    }

}

