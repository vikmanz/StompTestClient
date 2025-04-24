package com.vikmanz.stomptc.ui.components.income

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.json.JSONArray
import org.json.JSONObject

@Composable
fun PrettyJsonText(
    jsonString: String,
    style: TextStyle
) {

    val formattedJson = try {
        val jsonObject = JSONObject(jsonString)
        formatJson(jsonObject, 0)
    } catch (e: Exception) {
        jsonString
    }

    val lines = formattedJson.split("\n")

    Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .wrapContentHeight()
    ) {

        Row {
            Column {
                lines.forEachIndexed { index, _ ->
                    Text(
                            text = "${index + 1}",
                            style = style,
                            textAlign = TextAlign.End,
                            color = Color.Black,
                            modifier = Modifier
                                .width(40.dp)
                                .height(24.dp)
                                .background(Color.Companion.Gray.copy(alpha = 0.4f))
                                .padding(end = 8.dp)
                    )
                }
            }
            SelectionContainer {
                Column {
                    lines.forEachIndexed { _, line ->
                        Text(
                                text = line + '\n',
                                style = style,
                                fontFamily = FontFamily.Monospace,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(24.dp)
                        )
                    }
                }
            }
        }

    }
}

private fun formatJson(json: Any, indentLevel: Int): String {
    val indent = " ".repeat(indentLevel * 4)

    return when (json) {
        is JSONObject -> {
            val builder = StringBuilder()
            builder.append("{\n")
            val keys = json.keys().asSequence().toList()
            keys.forEachIndexed { index, key ->
                val value = json.get(key)
                builder.append("$indent    \"$key\": ${formatJson(value, indentLevel + 1)}")
                if (index < keys.size - 1) builder.append(",")
                builder.append("\n")
            }
            builder.append("$indent}")
            builder.toString()
        }
        is JSONArray  -> {
            val builder = StringBuilder()
            builder.append("[\n")
            json.forEachIndexed { index, value ->
                builder.append("$indent    ${formatJson(value, indentLevel + 1)}")
                if (index < json.length() - 1) builder.append(",")
                builder.append("\n")
            }
            builder.append("$indent]")
            builder.toString()
        }
        is String -> "\"$json\""
        else -> json.toString()
    }
}