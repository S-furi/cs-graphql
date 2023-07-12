package apollo.client

import com.apollographql.apollo3.api.Builder
import com.apollographql.apollo3.api.Optional
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

private val webSocketURL = "ws://localhost:8080/subscriptions"
private val graphQLEndpoint = "http://localhost:8080/graphql"

fun main() {

    val subClient =
            GraphQLClient.Builder()
                    .serverUrl(graphQLEndpoint)
                    .addHttpWithInterceptor()
                    .addSubscriptionModule(webSocketURL)
                    .build()
                    .client

    runBlocking {
        subClient.subscription(CounterSubscription(Optional.present(5))).toFlow().collect {
            println(it.data?.counter)
        }
    }

    subClient.close()
}
