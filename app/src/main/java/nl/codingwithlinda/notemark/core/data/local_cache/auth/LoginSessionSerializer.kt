package nl.codingwithlinda.notemark.core.data.local_cache.auth

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object LoginSessionSerializer : Serializer<LoginSession> {
    override val defaultValue: LoginSession
        get() = LoginSession()

    override suspend fun readFrom(input: InputStream): LoginSession {
        return try {
            Json.decodeFromString(
                deserializer = LoginSession.serializer(),
                string = input.readBytes().decodeToString()
            )
        }catch (e: SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: LoginSession, output: OutputStream) {
        output.run {
            write(
                Json.encodeToString(
                    serializer = LoginSession.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }


}