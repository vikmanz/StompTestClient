package com.vikmanz.stomptc.ui.components.income

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vikmanz.stomptc.app.DATE_TIME_FORMAT
import com.vikmanz.stomptc.model.StompFrame
import com.vikmanz.stomptc.ui.theme.COLOR_Button
import com.vikmanz.stomptc.ui.theme.COLOR_Card_bg
import java.time.LocalDateTime

@Composable
fun StompFrameItem(
    frame: StompFrame
) {
    Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp,
            border = BorderStroke(width = 0.5.dp, Color.Black)
    ) {

        Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
        ) {
            SelectionContainer {
                Text(
                        text = "${frame.headers["destination"] ?: "No Destination"} [${
                            LocalDateTime
                                .now()
                                .format(DATE_TIME_FORMAT)
                        }]:",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            PrettyJsonText(
                    jsonString = frame.body,
                    style = MaterialTheme.typography.h5
            )

        }
    }
}