
import com.vikmanz.stomptc.model.ConnectionModel
import com.vikmanz.stomptc.model.SendModel
import com.vikmanz.stomptc.model.SubscriptionModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object StorageService {
    private val json = Json { prettyPrint = true }

    fun save(config: ConnectionModel, subscriptions: List<SubscriptionModel>, messages: List<SendModel>) {
        val wrapper = StorageData(config, subscriptions, messages)
        File("stomp_config.json").writeText(json.encodeToString(wrapper))
    }

    fun load(): StorageData {
        val file = File("stomp_config.json")
        return if (file.exists()) {
            val wrapper = json.decodeFromString<StorageData>(file.readText())
            wrapper
        } else {
            StorageData(ConnectionModel(), emptyList(), emptyList())
        }
    }
}

@kotlinx.serialization.Serializable
data class StorageData(
    val config: ConnectionModel,
    val subscriptions: List<SubscriptionModel>,
    val sends: List<SendModel>
)
