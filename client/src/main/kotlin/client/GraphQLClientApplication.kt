package client

import DefaultGraphQLClient
import com.expediagroup.graphql.client.types.GraphQLClientRequest
import com.expediagroup.graphql.client.types.GraphQLClientResponse
import generated.*
import kotlinx.coroutines.runBlocking

fun main() {
    val client = DefaultGraphQLClient()

    println("*****Simple book retrieval example*****")
    val bookQueryResult =
            executeQuery(client, BookQuery(BookQuery.Variables(listOf(1, 2, 3))))
    printQueryResult(bookQueryResult)

    println("*****Simple Courses retrieval example*****")
    val courseQueryResult =
            executeQuery(client, CourseQuery(CourseQuery.Variables(listOf(1, 2, 3))))
    printQueryResult(courseQueryResult)

    client.close()
}

fun printQueryResult(queryResult: GraphQLClientResponse<*>): Unit {
    val data = queryResult.data
    when (data) {
        is BookQuery.Result? ->
                data?.searchBooks?.let { books ->
                    books.filterNotNull().map { it.title }.forEach(::println)
                }
        is CourseQuery.Result? ->
                data?.searchCourses?.let { courses -> courses.filterNotNull().forEach(::println) }
        else -> println("Unkown result\n: $data")
    }
}

fun <T : Any> executeQuery(
        client: DefaultGraphQLClient,
        query: GraphQLClientRequest<T>,
): GraphQLClientResponse<T> = runBlocking { client.execute(query) }
