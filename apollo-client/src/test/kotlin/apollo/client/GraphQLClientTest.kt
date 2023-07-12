package apollo.client

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll

import com.apollographql.apollo3.ApolloClient

import kotlinx.coroutines.runBlocking

class GraphQLClientTest {
    private val graphQLEndpoint = "http://localhost:8080/graphql"
    private val apolloClientBuilder =
        ApolloClient.Builder().serverUrl(graphQLEndpoint)
    
    @Test
    fun testSimpleBookQuery() {
        val expectedTitle = "Campbell Biology"

        val client = getDefaultClient()
        
        runBlocking {
            val received = client.query(GetBooksQuery(listOf(1))).execute()
            .data?.searchBooks?.get(0)?.title ?: throw IllegalStateException()

            assertTrue(received == expectedTitle)
        }
    }

    private fun getDefaultClient(): ApolloClient = apolloClientBuilder.build()
}
