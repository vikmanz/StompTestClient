package com.vikmanz.stomptc.ui.components.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vikmanz.stomptc.ui.theme.COLOR_Green

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckChange: (Boolean) -> Unit,
    text: String
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){

        Checkbox(
            checked = checked,
            onCheckedChange = { isChecked ->
                onCheckChange(isChecked)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = COLOR_Green,
                checkmarkColor = Color.Black
            )
        )

        Text(text)
    }
}


