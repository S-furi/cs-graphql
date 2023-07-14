package server.schema.queries

import com.expediagroup.graphql.server.operations.Subscription
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*

import kotlin.random.Random

class SimpleSubscription : Subscription {

    @GraphQLDescription("Return a single value")
    fun singleValue(): Flow<Int> = flowOf(1)


    @GraphQLDescription("Return a stream of valuess")
    fun multipleValue(): Flow<Int> = flowOf(1, 2, 3)

    @GraphQLDescription("Return a random number every second")
    fun counter(limit: Int? = null): Flow<Int> =
        (0..(limit ?: Int.MAX_VALUE)).asFlow().onEach { delay(1000) }
    
}
