package server.schema.queries

import com.expediagroup.graphql.server.operations.Mutation
import com.expediagroup.graphql.generator.annotations.GraphQLDescription

class SimpleMutation : Mutation {
    private val data = mutableListOf<String>()

    @GraphQLDescription("adds a string to a list, and returns the list")
    suspend fun addToList(value: String): MutableList<String> {
        data.add(value)
        return data
    }
}
