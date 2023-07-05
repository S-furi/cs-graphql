package server.schema.models

data class University(val id: Int, val name: String? = null) {
    companion object {
        suspend fun search(ids: List<Int>): List<University?> =
            listOf(
                University(id = 1, name = "University of Nebraska-Lincoln"),
                University(id = 2, name = "Kansas State University"),
                University(id = 3, name = "Purdue University"),
                University(id = 4, name = "Kennesaw State University"),
                University(id = 5, name = "University of Georgia")
            ).filter { ids.contains(it.id) }
    }
}
