package server

import com.expediagroup.graphql.dataloader.KotlinDataLoaderRegistryFactory
import com.expediagroup.graphql.generator.hooks.FlowSubscriptionSchemaGeneratorHooks
import com.expediagroup.graphql.server.ktor.DefaultKtorGraphQLContextFactory
import com.expediagroup.graphql.server.ktor.GraphQL
import com.expediagroup.graphql.server.ktor.graphQLGetRoute
import com.expediagroup.graphql.server.ktor.graphQLPostRoute
import com.expediagroup.graphql.server.ktor.graphQLSDLRoute
import com.expediagroup.graphql.server.ktor.graphQLSubscriptionsRoute
import com.expediagroup.graphql.server.ktor.graphiQLRoute
import io.ktor.serialization.jackson.JacksonWebsocketContentConverter
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.routing.Routing
import io.ktor.server.websocket.WebSockets
import server.schema.dataloaders.BookDataLoader
import server.schema.dataloaders.CourseDataLoader
import server.schema.dataloaders.UniversityDataLoader
import server.schema.queries.BookQueryService
import server.schema.queries.CourseQueryService
import server.schema.queries.DataAndErrorsQuery
import server.schema.queries.SimpleMutation
import server.schema.queries.SimpleSubscription
import server.schema.queries.UniversityQueryService
import server.schema.queries.MediaQueryService

fun Application.graphQLModule() {
    install(WebSockets) {
        pingPeriodMillis = 1000
        contentConverter = JacksonWebsocketContentConverter()
    }

    install(CORS) {
        anyHost() // to remove in production.
    }

    install(GraphQL) {
        schema {
            packages = listOf("server")
            queries =
                    listOf(
                            BookQueryService(),
                            CourseQueryService(),
                            UniversityQueryService(),
                            DataAndErrorsQuery(),
                            MediaQueryService(),
                    )
            mutations = listOf(SimpleMutation())
            subscriptions = listOf(SimpleSubscription())
            hooks = FlowSubscriptionSchemaGeneratorHooks()
        }
        engine {
            automaticPersistedQueries { enabled = true }
            dataLoaderRegistryFactory =
                    KotlinDataLoaderRegistryFactory(
                            UniversityDataLoader,
                            CourseDataLoader,
                            BookDataLoader
                    )
        }
        server { contextFactory = DefaultKtorGraphQLContextFactory() }
    }

    install(Routing) {
        graphQLGetRoute()
        graphQLPostRoute()
        graphQLSubscriptionsRoute()
        graphiQLRoute()
        graphQLSDLRoute()
    }
}
