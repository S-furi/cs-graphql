package apollo.client

import com.apollographql.apollo3.api.Builder
import com.apollographql.apollo3.api.Optional
import kotlinx.coroutines.runBlocking

private val webSocketURL = "ws://localhost:8080/subscriptions"
private val graphQLEndpoint = "http://localhost:8080/graphql"

fun main() = runBlocking {
    val subClient =
            GraphQLClient.Builder()
                    .serverUrl(graphQLEndpoint)
                    .addHttpWithInterceptor()
                    .addSubscriptionModule(webSocketURL)
                    .build()
                    .client

    subClient.subscription(CounterSubscription(Optional.present(5))).toFlow().collect {
        println("Received: ${it.data?.counter} from server")
    }

    subClient.close()
}
