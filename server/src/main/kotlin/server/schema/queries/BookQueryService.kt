package server.schema.queries

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import java.util.concurrent.CompletableFuture
import server.schema.dataloaders.BookDataLoader
import server.schema.models.Book

class BookQueryService : Query {
    @GraphQLDescription("Return list of books based on parameters options")
    fun searchBooks(
            params: BookSearchParameters,
            dfe: DataFetchingEnvironment
    ): CompletableFuture<List<Book>> =
            dfe.getDataLoader<Int, Book>(BookDataLoader.dataLoaderName)
                .loadMany(params.ids)
}

data class BookSearchParameters(val ids: List<Int>)
