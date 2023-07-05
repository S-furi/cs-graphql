package server.schema.dataloaders

import com.expediagroup.graphql.dataloader.KotlinDataLoader
import graphql.GraphQLContext
import java.util.concurrent.CompletableFuture
import kotlinx.coroutines.runBlocking
import org.dataloader.DataLoaderFactory
import server.schema.models.University

val UniversityDataLoader =
        object : KotlinDataLoader<Int, University?> {
            override val dataLoaderName = "UNIVERSITY_LOADER"
            override fun getDataLoader(graphQLContext: GraphQLContext) =
                    DataLoaderFactory.newDataLoader { ids ->
                        CompletableFuture.supplyAsync {
                            runBlocking { University.search(ids).toMutableList() }
                        }
                    }
        }
