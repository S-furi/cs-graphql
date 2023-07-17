package apollo.client

import kotlinx.coroutines.runBlocking

private const val webSocketURL = "ws://localhost:8080/subscriptions"
private const val graphQLEndpoint = "http://localhost:8080/graphql"

fun main() = runBlocking {
    val subClient =
            GraphQLClient.Builder()
                    .serverUrl(graphQLEndpoint)
                    .addHttpWithInterceptor()
                    .addSubscriptionModule(webSocketURL)
                    .build()
                    .client

    val res = subClient.query(MediaQuery(listOf(1, 2))).execute()
        .dataOrThrow().medias

    res.forEach {
        print("Title: ${it.title}, (${it.duration} minutes)")
        when(it.__typename) {
            "Movie" -> println(" Director: ${it.onMovie?.director}")
            "Audio" -> println(" Filetype: ${it.onAudio?.fileType}")
            else -> println("Result not known")
        }
    }

    subClient.close()
}