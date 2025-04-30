import androidx.compose.runtime.MutableState
import com.vikmanz.stomptc.model.ConnectionModel
import com.vikmanz.stomptc.model.SendModel
import com.vikmanz.stomptc.model.StompFrame
import com.vikmanz.stomptc.model.StompFrameBuilder
import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.collections.set

object StompService {
    private var client: HttpClient? = null
    private var session: WebSocketSession? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    private val topicCallbacks = mutableMapOf<String, MutableState<String>>()

    private val subscriptions = ConcurrentHashMap<String, String>() // id -> topic

    private val _receivedMessages = MutableStateFlow(ConcurrentLinkedQueue<StompFrame>())
    val receivedMessages: StateFlow<List<StompFrame>> =
        _receivedMessages
            .map { it.toList() }
            .stateIn(
                    scope = CoroutineScope(Dispatchers.IO),
                    started = SharingStarted.Eagerly,
                    initialValue = emptyList()
            )

    suspend fun connect(config: ConnectionModel) {
        if (session != null || client != null) {
            disconnect()
        }
        initValues(config)

        val defaultHeaders = mapOf(
            "accept-version" to "1.2",
            "host" to extractHost(config.endpoint)
        )

        val mergedHeaders = defaultHeaders + config.headers.associate { it.key to it.value }

        sendStompFrame("CONNECT", headers = mergedHeaders)
        listen()
    }

    suspend fun disconnect() {
        sendStompFrame("DISCONNECT")
        session?.close()
        client?.close()
        session = null
        client = null
        topicCallbacks.clear()
        subscriptions.clear()
        clearIncomingMessages()
    }

    suspend fun send(message: SendModel) {
        val extraHeaders = message.headers.associate { it.key to it.value }
        val allHeaders = mapOf("destination" to message.topic) + extraHeaders

        sendStompFrame(
                command = "SEND",
                headers = allHeaders,
                body = message.message
        )
    }

    suspend fun subscribe(topic: String, id: String = "sub-${topic.hashCode()}") {
        if (subscriptions.contains(topic)) {
            println("Topic $topic is already subscribed!")
            return
        }
        sendStompFrame(
                command = "SUBSCRIBE",
                headers = mapOf(
                        "id" to id,
                        "destination" to topic
                )
        )
        subscriptions[topic] = id
    }

    suspend fun unsubscribe(topic: String) {
        val id = subscriptions[topic] ?: ""
        sendStompFrame(
                command = "UNSUBSCRIBE",
                headers = mapOf("id" to id)
        )
        subscriptions.remove(topic)
    }


    fun clearIncomingMessages() {
        _receivedMessages.update { ConcurrentLinkedQueue() }
    }

    private suspend fun initValues(config: ConnectionModel) {
        client = HttpClient {
            install(WebSockets)
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
        session = client?.webSocketSession {
            url(config.endpoint)
            config.headers.forEach { (k, v) ->
                header(k, v)
            }
        }
    }

    private suspend fun sendStompFrame(
        command: String,
        headers: Map<String, String> = emptyMap(),
        body: String? = null
    ) {
        try {

            val frame = StompFrameBuilder.build(command) {
                headers(headers)
                body?.let { body(it) }
            }
            println("\n=====\nSend:\n$frame\n=====\n")
            session?.send(Frame.Text(frame))
        } catch (e: Exception) {
            println("❌ Error sending frame: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun extractHost(url: String): String {
        return try {
            java.net.URI(url).host ?: "localhost"
        } catch (e: Exception) {
            "localhost"
        }
    }

    private fun handleIncomingMessage(rawMessage: String) {
        val frame = StompFrame.parse(rawMessage)
        if (frame.command == "MESSAGE") {
            _receivedMessages.update { queue ->
                val newQueue = ConcurrentLinkedQueue(queue)
                newQueue.add(frame)
                newQueue
            }
        }
    }

    private fun listen() {
        scope.launch {
            try {
                session?.incoming?.consumeEach { frame ->
                    if (frame is Frame.Text) {
                        val message = frame.readText()
                        println("=====\nReceived:\n$message\n=====")
                        handleIncomingMessage(message)
                    }
                }
            } catch (e: Exception) {
                println("Error receiving: ${e.message}")
                e.printStackTrace()
            }
        }
    }

}