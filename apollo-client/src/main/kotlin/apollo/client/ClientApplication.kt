package apollo.client

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Builder
import com.apollographql.apollo3.api.Optional
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import apollo.client.getHttpClient

private val webSocketURL = "ws://localhost:8080/subscriptions"
private val graphQLEndpoint = "http://localhost:8080/graphql"

fun getHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
                .addInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .build()

fun main() {

    val subClient = GraphQLClient.Builder()
        .serverUrl(graphQLEndpoint)
        .addSubscriptionModule(webSocketURL, getHttpClient())
        .build().client

    runBlocking {
        subClient.subscription(CounterSubscription(Optional.present(10))).toFlow()
            .collect { println(it.data?.counter) }
    }

    subClient.close()
}
