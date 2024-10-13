import androidx.datastore.core.Serializer
import com.ru.hazakura.domain.model.AccessAndRefreshToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object AccessAndRefreshTokenSerializer: Serializer<AccessAndRefreshToken> {
    override val defaultValue: AccessAndRefreshToken
        get() = AccessAndRefreshToken()

    override suspend fun readFrom(input: InputStream): AccessAndRefreshToken {
        return try {
            // Преобразуем байты в строку, используя правильную кодировку
            val jsonString = input.bufferedReader(Charsets.UTF_8).use { it.readText() }
            Json.decodeFromString(
                deserializer = AccessAndRefreshToken.serializer(),
                string = jsonString
            )
        } catch (e: SerializationException) {
            AccessAndRefreshToken()
        }
    }

    override suspend fun writeTo(t: AccessAndRefreshToken, output: OutputStream) {
        withContext(Dispatchers.IO) {
            // Преобразуем строку в байты, используя правильную кодировку
            val jsonString = Json.encodeToString(
                serializer = AccessAndRefreshToken.serializer(),
                value = t
            )
            output.write(jsonString.toByteArray(Charsets.UTF_8))
        }
    }
}
