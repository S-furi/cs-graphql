package apollo.client

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Builder
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

private val webSocketURL = "ws://localhost:8080/graphql"
private val graphQLEndpoint = "http://localhost:8080/graphql"

fun getHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
                .addInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .build()

fun main() {

    val client = GraphQLClient.Builder().serverUrl(graphQLEndpoint).build().client

    // Simple Query test
    runBlocking {
        client.query(CourseQuery(listOf(1, 2, 3)))
                .execute()
                .dataOrThrow()
                .searchCourses
                .forEach(::println)
    }

    // Simple Mutation test
    runBlocking {
        client.mutation(ListAddMutation("Hello")).execute().dataOrThrow().also(::println)
    }

    // Subscriptions test (not working)
    // runBlocking {
    //     client.subscription(CounterSubscription(Optional.present(10))).toFlow()
    //         .collect(::printSubscriptionDetails)
    // }}

    client.close()
}

fun printSubscriptionDetails(data: ApolloResponse<CounterSubscription.Data>): Unit {
    println(
            """
    Errors: ${data.errors}

    Data: ${data.data}

    IsLast: ${data.isLast}

    Exception: ${data.exception}

    Operation: ${data.operation}

    Exec. Content: ${data.executionContext}

    RequestUuid: ${data.requestUuid}
    """
    )
}
