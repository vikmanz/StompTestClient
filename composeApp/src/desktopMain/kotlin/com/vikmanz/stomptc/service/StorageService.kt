
import com.vikmanz.stomptc.model.ConnectionModel
import com.vikmanz.stomptc.model.StompMessageModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object StorageService {
    private val json = Json { prettyPrint = true }

    fun save(config: ConnectionModel, messages: List<StompMessageModel>) {
        val wrapper = StorageWrapper(config, messages)
        File("stomp_config.json").writeText(json.encodeToString(wrapper))
    }

    fun load(): Pair<ConnectionModel, List<StompMessageModel>> {
        val file = File("stomp_config.json")
        return if (file.exists()) {
            val wrapper = json.decodeFromString<StorageWrapper>(file.readText())
            wrapper.config to wrapper.messages
        } else {
            ConnectionModel() to emptyList()
        }
    }

    @kotlinx.serialization.Serializable
    data class StorageWrapper(
        val config: ConnectionModel,
        val messages: List<StompMessageModel>
    )
}
