package client

import com.expediagroup.graphql.client.ktor.GraphQLKtorClient
import com.expediagroup.graphql.client.serialization.GraphQLClientKotlinxSerializer
import com.expediagroup.graphql.client.types.GraphQLClientResponse
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

val logger = Logger.DEFAULT

fun main() {
    val httpClient = getDefaultHttpClient()

    val client =
            GraphQLKtorClient(
                    url = URL("http://localhost:8080/graphql"),
                    httpClient = httpClient,
                    serializer = GraphQLClientKotlinxSerializer()
            )

    logger.log("Simple book retrieval example")

    val queryResult = executeBookQuery(client, SimpleBookQuery.Variables(listOf(1, 2, 3)))
    queryResult.data?.searchBooks?.let { books ->
        books.filterNotNull().map { it.title }.forEach(logger::log)
    }

    client.close()
}

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

fun executeBookQuery(
        client: GraphQLKtorClient,
        vars: SimpleBookQuery.Variables
): GraphQLClientResponse<SimpleBookQuery.Result> = runBlocking {
    client.execute(SimpleBookQuery(variables = vars))
}
