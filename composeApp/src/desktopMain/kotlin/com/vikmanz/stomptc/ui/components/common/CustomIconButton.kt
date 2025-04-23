package com.vikmanz.stomptc.ui.components.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun CustomIconButtonPreview() {
    MaterialTheme {
        CustomIconButton()
    }
}

@Composable
fun CustomIconButton(
    icon: ImageVector = Icons.Default.Close,
    color: Color = Color.Transparent,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    IconButton(
            onClick = onClick,
            modifier = Modifier
                .background(color)
                .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = MaterialTheme.shapes.medium
                )
                .then(modifier)
    ) {
        Icon(
                icon,
                contentDescription = null
        )
    }
}

