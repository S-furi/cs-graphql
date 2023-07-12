package apollo.client

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.apollographql.apollo3.network.ws.GraphQLWsProtocol
import com.apollographql.apollo3.network.ws.WebSocketNetworkTransport
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class GraphQLClient(val client: ApolloClient) {

    private constructor(builder: Builder): this(builder.apolloClientBuilder.build())

    class Builder() {
        val apolloClientBuilder: ApolloClient.Builder = ApolloClient.Builder()

        fun serverUrl(serverUrl: String) = apply { apolloClientBuilder.serverUrl(serverUrl) }

        fun addSubscriptionModule(webSocketEndpoint: String, okHttpClient: OkHttpClient? = null) =
                apply {
                    apolloClientBuilder.subscriptionNetworkTransport(
                            WebSocketNetworkTransport.Builder()
                                    .serverUrl(webSocketEndpoint)
                                    .protocol(GraphQLWsProtocol.Factory())
                                    .also {
                                        if (okHttpClient != null) it.okHttpClient(okHttpClient)
                                    }
                                    .build()
                    )
                }

        fun build() = GraphQLClient(this)
    }
}
