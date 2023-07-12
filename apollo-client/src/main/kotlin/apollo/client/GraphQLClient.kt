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

        /**
        *
        * Adds WebSocket Network Transport for enabling subscriptions.
        * 
        * Note: subscriptions-transport-ws was deprecated in favor of graphql-ws
        * protocol, but because of backward compatibility, Apollo by default 
        * uses the deprecated protocol. In this implemention, we explicitly
        * specify the correct version that is now being the HTTP standard.
        */
        fun addSubscriptionModule(webSocketEndpoint: String, okHttpClient: OkHttpClient? = null) =
                apply {
                    apolloClientBuilder.subscriptionNetworkTransport(
                            WebSocketNetworkTransport.Builder()
                                    .serverUrl(webSocketEndpoint)
                                    // specifying the new graphql-ws protocol
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
