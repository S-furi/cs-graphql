package server.schema.dataloaders

import com.expediagroup.graphql.dataloader.KotlinDataLoader
import graphql.GraphQLContext
import java.util.concurrent.CompletableFuture
import org.dataloader.DataLoaderFactory
import server.schema.models.Book

val BookDataLoader =
        object : KotlinDataLoader<Int, Book?> {
            override val dataLoaderName = "BOOK_LOADER"
            override fun getDataLoader(graphQLContext: GraphQLContext) =
                    DataLoaderFactory.newDataLoader { ids ->
                        CompletableFuture.supplyAsync { Book.search(ids).toMutableList() }
                    }
        }
