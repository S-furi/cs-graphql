package client

import DefaultGraphQLClient
import com.expediagroup.graphql.client.types.GraphQLClientResponse
import com.expediagroup.graphql.client.types.GraphQLClientRequest
import generated.*
import kotlinx.coroutines.runBlocking
import client.executeCourseQuery

fun main() {
    val client = DefaultGraphQLClient()
    val logger = client.logger

    logger.log("*****Simple book retrieval example*****")
    val bookQueryResult = executeBookQuery(client, BookQuery.Variables(listOf(1, 2, 3)))
    bookQueryResult.data?.searchBooks?.let { books ->
        books.filterNotNull().map { it.title }.forEach(logger::log)
    }

    logger.log("*****Simple Courses retrieval example*****")
    val courseQuery = executeCourseQuery(client, CourseQuery.Variables(listOf(1, 2, 3)))
    courseQuery.data?.searchCourses?.let { courses ->
        courses.filterNotNull() .forEach(::println)
    }

    client.close()
}

fun executeBookQuery(
        client: DefaultGraphQLClient,
        vars: BookQuery.Variables
): GraphQLClientResponse<BookQuery.Result> = runBlocking {
    client.execute(BookQuery(variables = vars))
}

fun executeCourseQuery(
    client: DefaultGraphQLClient,
    vars: CourseQuery.Variables
): GraphQLClientResponse<CourseQuery.Result> = runBlocking {
    client.execute(CourseQuery(variables = vars))
}
