package client

import com.expediagroup.graphql.client.ktor.GraphQLKtorClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import java.net.URL
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.runBlocking

import generated.SimpleBookQuery

fun main() {
    val httpClient =
            HttpClient(engineFactory = OkHttp) {
                engine {
                    config {
                        connectTimeout(10, TimeUnit.SECONDS)
                        readTimeout(60, TimeUnit.SECONDS)
                        writeTimeout(60, TimeUnit.SECONDS)
                    }
                }
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.HEADERS
                }
            }

    val client =
            GraphQLKtorClient(url = URL("http://localhost:8080/graphql"), httpClient = httpClient)

    println("Simlpe hello book example")
    runBlocking {
        val bookQuery = SimpleBookQuery()
        val queryResult = client.execute(bookQuery)
        queryResult.data?.searchBooks?.forEach(System.out::println)
    }
}