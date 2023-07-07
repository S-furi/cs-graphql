package client

import DefaultGraphQLClient
import com.expediagroup.graphql.client.types.GraphQLClientResponse
import generated.*
import kotlinx.coroutines.runBlocking

fun main() {
    val client = DefaultGraphQLClient()
    val logger = client.logger

    logger.log("Simple book retrieval example")

    val queryResult = executeBookQuery(client, SimpleBookQuery.Variables(listOf(1, 2, 3)))
    queryResult.data?.searchBooks?.let { books ->
        books.filterNotNull().map { it.title }.forEach(logger::log)
    }

    client.close()
}

fun executeBookQuery(
        client: DefaultGraphQLClient,
        vars: SimpleBookQuery.Variables
): GraphQLClientResponse<SimpleBookQuery.Result> = runBlocking {
    client.execute(SimpleBookQuery(variables = vars))
}
