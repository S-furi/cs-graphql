import com.expediagroup.graphql.client.ktor.GraphQLKtorClient
import com.expediagroup.graphql.client.types.GraphQLClientRequest
import generated.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import java.net.URL
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.runBlocking


class DefaultGraphQLClient(val host: String = "localhost", val port: Int = 8080) {
    private val ktorClient: GraphQLKtorClient
    val logger = Logger.DEFAULT

    init {
        ktorClient = GraphQLKtorClient(url = getUrl(), httpClient = getDefaultHttpClient())
    }

    fun getUrl(): URL = URL("http://$host:$port/graphql")

    fun getDefaultHttpClient() =
            HttpClient(engineFactory = OkHttp) {
                engine {
                    config {
                        connectTimeout(10, TimeUnit.SECONDS)
                        readTimeout(60, TimeUnit.SECONDS)
                        writeTimeout(60, TimeUnit.SECONDS)
                    }
                }
                install(Logging) {
                    logger = logger
                    level = LogLevel.HEADERS
                }
            }

    fun <T : Any> execute(request: GraphQLClientRequest<T>) = runBlocking {
        ktorClient.execute(request)
    }

    fun close() = ktorClient.close()
}
