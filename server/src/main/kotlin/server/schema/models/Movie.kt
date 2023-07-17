package server.schema.models

data class Movie(
    override val id: Int,
    override val title: String? = null,
    override val duration: Int? = null,
    val director: String? = null,
    val studio: String? = null,
) : Media

class MovieService {
    private val movies = listOf(
        Movie(1, "Inception", 230, "Nolan", "Warner Bros."),
        Movie(2, "The Dark Knight", 250, "Nolan"),
        Movie(3, "I magotti", 29),
    )

    fun getMovies(ids: List<Int>) = movies.filter { ids.contains(it.id) }
}
