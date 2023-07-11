package apollo.client

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.ws.WebSocketNetworkTransport
import com.apollographql.apollo3.network.http.HttpNetworkTransport

import apollo.client.CourseQuery
import kotlinx.coroutines.runBlocking

import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.Builder
import com.apollographql.apollo3.network.ws.GraphQLWsProtocol
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

fun main() {
    val okHttpClient = OkHttpClient.Builder().build()
    val url = "://localhost:8080/graphql"


    val apolloClient = ApolloClient.Builder()
        .serverUrl("http$url")
        .okHttpClient(okHttpClient)
        .webSocketServerUrl("wss$url")
        .subscriptionNetworkTransport(
            WebSocketNetworkTransport.Builder()
                .protocol(GraphQLWsProtocol.Factory()) // implemented by default with graphql-kotlin
                .serverUrl("wss$url")
                .build()
        )
        .build()

    apolloClient.close()
}
