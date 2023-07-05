package server.schema.dataloaders

import com.expediagroup.graphql.dataloader.KotlinDataLoader
import java.util.concurrent.CompletableFuture
import org.dataloader.DataLoaderFactory
import kotlinx.coroutines.runBlocking
import graphql.GraphQLContext
import server.schema.models.Course


val CourseDataLoader = object : KotlinDataLoader<Int, Course?> {
    override val dataLoaderName = "COURSE_LOADER"
    override fun getDataLoader(graphQLContext: GraphQLContext) =
        DataLoaderFactory.newDataLoader { ids ->
            CompletableFuture.supplyAsync {
                runBlocking { Course.search(ids).toMutableList() }
            }
        }
}
