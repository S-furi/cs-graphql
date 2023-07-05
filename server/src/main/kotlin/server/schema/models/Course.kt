package server.schema.models

import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import com.expediagroup.graphql.server.extensions.getValuesFromDataLoader
import graphql.schema.DataFetchingEnvironment
import java.util.concurrent.CompletableFuture
import server.schema.dataloaders.UniversityDataLoader
import server.schema.dataloaders.BookDataLoader

data class Course(
        val id: Int,
        val name: String?,
        val universityId: Int?,
        val bookIds: List<Int> = emptyList()
) {
    fun university(dfe: DataFetchingEnvironment): CompletableFuture<University> {
        return if (universityId != null) {
            dfe.getValueFromDataLoader<Int, University>(
                    UniversityDataLoader.dataLoaderName,
                    universityId
            )
        } else CompletableFuture.completedFuture(null)
    }

    fun books(dfe: DataFetchingEnvironment): CompletableFuture<List<Book>> =
        dfe.getValuesFromDataLoader(BookDataLoader.dataLoaderName, bookIds)

    companion object {
        fun search(ids: List<Int>): List<Course> =
            listOf(
                Course(id = 1, name = "Biology 101", universityId = 1, bookIds = listOf(1, 2)),
                Course(id = 2, name = "Cultural Anthropology", universityId = 1),
                Course(id = 3, name = "Computer Science 101", universityId = 1, bookIds = listOf(3, 4)),
                Course(id = 4, name = "Computer Science 101", universityId = 3, bookIds = listOf(3, 4))
            ).filter { ids.contains(it.id) }
    }
}
