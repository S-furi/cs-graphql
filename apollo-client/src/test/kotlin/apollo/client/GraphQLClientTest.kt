package apollo.client

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GraphQLClientTest {
    private val graphQLEndpoint = "http://localhost:8080/graphql"
    private val webSocketEndpoint = "ws://localhost:8080/subscriptions"
    private val clientBuilder = GraphQLClient.Builder().serverUrl(graphQLEndpoint)

    private class ServerNotRunningException(message: String = "Server must be up and running") :
            Exception(message)

    @Test
    fun testSimpleBookQuery() {
        val expectedTitle = "Campbell Biology"

        val client = getDefaultClient()

        runBlocking {
            val received =
                    client.query(GetBooksQuery(listOf(1)))
                            .execute()
                            .data
                            ?.searchBooks
                            ?.get(0)
                            ?.title
                            ?: throw ServerNotRunningException()

            assertTrue(received == expectedTitle)
        }

        client.close()
    }

    @Test
    fun testSimpleMutation() {
        val client = getDefaultClient()

        runBlocking {
            var received =
                    client.mutation(ListAddMutation("Hello")).execute().data?.addToList
                            ?: throw ServerNotRunningException()

            assertTrue(listOf("Hello").equals(received))

            received =
                    client.mutation(ListAddMutation("Word")).execute().data?.addToList
                            ?: throw ServerNotRunningException()

            assertEquals(listOf("Hello", "Word"), received, "Got $received")
        }

        client.close()
    }

    @Test
    fun testSimpleSubscription() {
        val client = clientBuilder.addSubscriptionModule(webSocketEndpoint).build().client

        runBlocking {
            val received =
                    client.subscription(CounterSubscription(Optional.present(2)))
                            .toFlow()
                            .toList()
                            .map { it.data?.counter }
            assertEquals(List(3) { it }, received)
        }
    }

    private fun getDefaultClient(): ApolloClient = clientBuilder.build().client
}
