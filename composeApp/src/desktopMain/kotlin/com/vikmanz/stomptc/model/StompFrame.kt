package com.vikmanz.stomptc.model

import com.vikmanz.stomptc.app.NULL_CHAR

data class StompFrame(
    val command: String,
    val headers: Map<String, String>,
    val body: String
) {
    companion object {
        fun parse(raw: String): StompFrame {
            val parts = raw.split("\n\n", limit = 2)
            val headerLines = parts.getOrNull(0)?.lines() ?: emptyList()
            val body = parts.getOrNull(1)?.removeSuffix(NULL_CHAR.toString()) ?: ""

            val command = headerLines.firstOrNull() ?: "UNKNOWN"
            val headers = headerLines.drop(1).mapNotNull {
                val (key, value) = it.split(":", limit = 2).let {
                    if (it.size == 2) it[0] to it[1] else null
                } ?: return@mapNotNull null
                key to value
            }.toMap()

            return StompFrame(command, headers, body)
        }
    }
}

class StompFrameBuilder(
    private val command: String
) {
    private val headers = mutableMapOf<String, String>()
    private var body: String? = null

    fun header(key: String, value: String): StompFrameBuilder {
        headers[key] = value
        return this
    }

    fun headers(map: Map<String, String>): StompFrameBuilder {
        headers.putAll(map)
        return this
    }

    fun body(body: String): StompFrameBuilder {
        this.body = body
        return this
    }

    fun build(): String {
        val builder = StringBuilder()
        builder.append(command).append("\n")
        headers.forEach { (key, value) ->
            builder.append("$key:$value\n")
        }
        builder.append("\n")
        body?.let { builder.append(it) }
        builder.append(NULL_CHAR)
        return builder.toString()
    }

    companion object {
        inline fun build(command: String, builderAction: StompFrameBuilder.() -> Unit): String {
            val builder = StompFrameBuilder(command)
            builder.builderAction()
            return builder.build()
        }
    }
}