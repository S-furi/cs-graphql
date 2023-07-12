# Client-Server GraphQL Architecture

A simple project illustrating how it's possibile to create a Ktor Web Server
that provides GraphQL APIs over HTTP (POST), and an Apollo Client that can
perform queries, mutations and subscriptions over the GraphQL server.

### Server
[Ktor](https://github.com/ktorio/ktor) has been used to build the Web Server.
This leads to a lighter (compared to Spring) and simpler implementation of the
GraphQL server.
Thanks to [GraphQL Kotlin](https://github.com/ExpediaGroup/graphql-kotlin)
building and setting up routes to the GraphQL endpoint is made easy.

For each element of the schema, we must build the model class (hopefully a data
class), a data loader, for defining data fetching strategies, and the
operations (queries, mutations or subscriptions) on top of them.

Every dataloader and operation must be included during the server's setup.

#### Instructions
Read [server instructions](./server/README.md) for building and running the server.

### Client
Being *subscriptions* not already supported by GraphQL Kotlin, 
[Apollo GraphQL](https://github.com/apollographql/apollo-kotlin) has been used,
providing an even simpler way to handle GraphQL client side requests.

This library provides a gradle plugin that auto-generate all the schema classes
and informations for providing, at compile time, static typing safety.

Once the schema is downloaded, and the gradle project is built, all the needed
classes for quering the server will be generated inside the `build/generated`
gradle folder. At this point, it's possibile to query the Server for both
queries and mutations but also subscriptions.

#### Instructions
Read [client instructions](./apollo-client/README.md) for building and running
the client.

---
## Future Developements
- Explore Apollo Kotlin **caching** mechanisms and evaluate a self implementation
  using an in-memory DB like Redis.
- Attaching a database
    - Building a docker architecture for DB and Server (<--gradle tasks).
- Implement authorization and authentication.
