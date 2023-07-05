package server.schema.models

data class Book(
    val id: Int,
    val title: String
) {
    companion object {
        fun search(ids: List<Int>): List<Book> {
            return listOf(
                Book(id = 1, title = "Campbell Biology"),
                Book(id = 2, title = "The Cell"),
                Book(id = 3, title = "Data Structures in C++"),
                Book(id = 4, title = "The Algorithm Design Manual")
            ).filter { ids.contains(it.id) }
        }
    }
}
