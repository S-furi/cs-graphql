package server.schema.queries

import com.expediagroup.graphql.server.operations.Query
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import graphql.schema.DataFetchingEnvironment
import java.util.concurrent.CompletableFuture
import server.schema.models.University
import server.schema.dataloaders.UniversityDataLoader

class UniversityQueryService : Query {
    @GraphQLDescription("Return list of universities based on parameters options")
    fun searchUniversities(
        params: UniversitySearchParameters,
        dfe: DataFetchingEnvironment
    ): CompletableFuture<List<University>> =
        dfe.getDataLoader<Int, University>(UniversityDataLoader.dataLoaderName)
            .loadMany(params.ids)
        
}

data class UniversitySearchParameters(val ids: List<Int>)
