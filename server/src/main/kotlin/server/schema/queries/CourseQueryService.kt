package server.schema.queries

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import java.util.concurrent.CompletableFuture
import server.schema.dataloaders.CourseDataLoader
import server.schema.models.Course

class CourseQueryService : Query {
    @GraphQLDescription("Return list of courses based on parameters options")
    fun searchCourses(
            params: CourseSearchParameters,
            dfe: DataFetchingEnvironment
    ): CompletableFuture<List<Course>> =
            dfe.getDataLoader<Int, Course>(CourseDataLoader.dataLoaderName)
                .loadMany(params.ids)
}

data class CourseSearchParameters(val ids: List<Int>)
