package apollo.client

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.ws.WebSocketNetworkTransport
import com.apollographql.apollo3.network.http.HttpNetworkTransport
import com.apollographql.apollo3.network.http.ApolloClientAwarenessInterceptor

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay

import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.Builder
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.network.ws.GraphQLWsProtocol
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

fun main() {
    val apolloClient = ApolloClient.Builder()
        .serverUrl("http://localhost:8080/graphql")
        .subscriptionNetworkTransport(
            WebSocketNetworkTransport.Builder()
                .protocol(GraphQLWsProtocol.Factory())
                .serverUrl("ws://localhost:8080/graphql")
                .build()
        ).build()



    // Simple Query test
    runBlocking {
        apolloClient.query(CourseQuery(listOf(1, 2, 3)))
            .execute()
            .dataOrThrow()
            .searchCourses.forEach(::println)
    }

    // Simple Mutation test
    runBlocking {
        apolloClient.mutation(ListAddMutation("Hello"))
            .execute()
            .dataOrThrow()
            .also(::println)
    }

    // Subscriptions test (not working)
    // runBlocking {
    //     apolloClient.subscription(CounterSubscription(Optional.present(10))).toFlow()
    //         .collect(::printSubscriptionDetails)
    // }

    apolloClient.close()
}

fun printSubscriptionDetails(data: ApolloResponse<CounterSubscription.Data>): Unit {
    println("""
    Errors: ${data.errors}

    Data: ${data.data}

    IsLast: ${data.isLast}

    Exception: ${data.exception}

    Operation: ${data.operation}

    Exec. Content: ${data.executionContext}

    RequestUuid: ${data.requestUuid}
    """)
}

