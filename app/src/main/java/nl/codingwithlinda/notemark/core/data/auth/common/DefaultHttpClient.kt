package nl.codingwithlinda.notemark.core.data.auth.common

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.client.utils.buildHeaders
import io.ktor.serialization.kotlinx.json.json
import nl.codingwithlinda.notemark.BuildConfig

object DefaultHttpClient {

    val httpClient = HttpClient(CIO) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(DefaultRequest){
            buildHeaders(){
                header("Content-Type", "application/json")
                header("X-User-Email", BuildConfig.AUTH_API_EMAIL)
            }
        }
        install(Resources)
        install(ContentNegotiation){
            json()
        }
    }
}