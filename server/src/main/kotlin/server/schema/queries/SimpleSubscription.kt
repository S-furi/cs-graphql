package server.schema.queries

import com.expediagroup.graphql.server.operations.Subscription
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay

import kotlin.random.Random

class SimpleSubscription : Subscription {

    @GraphQLDescription("Return a single value")
    fun singleValue(): Flow<Int> = flowOf(1)


    @GraphQLDescription("Return a stream of valuess")
    fun multipleValue(): Flow<Int> = flowOf(1, 2, 3)

    @GraphQLDescription("Return a random number every second")
    fun counter(limit: Int? = null): Flow<Int> = flow {
        for (i in 0..(limit ?: Int.MAX_VALUE)) {
            emit(i)
            delay(1000)
        }
    }
}
