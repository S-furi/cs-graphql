package server.schema.queries

import com.expediagroup.graphql.server.operations.Query
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import graphql.execution.DataFetcherResult
import graphql.GraphQLError
import graphql.GraphqlErrorException

import java.util.concurrent.CompletableFuture

/**
* This class demonstrate the integration of errors inside the Result of a 
* data fetching operation.
*/
class DataAndErrorsQuery : Query {

    @GraphQLDescription("Returns data and error fields")
    fun returnDataAndError(): DataFetcherResult<String?> {
        return DataFetcherResult.newResult<String>()
            .data("Data from the dataFetcher")
            .error(getError())
            .build()
    }

    @GraphQLDescription("Returns data and error fields, in the form of a Completable Future object")
    fun completableFutureDataAndErrors(): CompletableFuture<DataFetcherResult<String>> {
        val dfr = DataFetcherResult.newResult<String>()
            .data("Data from the dataFetcher")
            .error(getError())
            .build()

        return CompletableFuture.completedFuture(dfr)
    }

    private fun getError(): GraphQLError = GraphqlErrorException.newErrorException()
        .cause(RuntimeException("data and errors"))
        .message("data and errors")
        .build()
}
