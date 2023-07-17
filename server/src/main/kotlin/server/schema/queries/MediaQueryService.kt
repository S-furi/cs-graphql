package server.schema.queries

import com.expediagroup.graphql.server.operations.Query
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import server.schema.models.Media
import server.schema.models.MovieService
import server.schema.models.AudioService

class MediaQueryService: Query {
    private val audioService = AudioService()
    private val movieService = MovieService()

    @GraphQLDescription("Returns media specifying the ids of them")
    fun medias(ids: List<Int>): List<Media> =
        listOf(audioService.getAudios(ids), movieService.getMovies(ids)).flatten()
}
