# Client-Server GraphQL Architecture

## Libs

- [GraphQL-Kotlin](https://github.com/ExpediaGroup/graphql-kotlin)
- Spring or [Ktor](https://github.com/ktorio/ktor) for the web Server

### Ktor Version
Simple client/server architecture where the schema is defined through a series
of classes (or kotlin data classes) exposing methods for fetching data given a
series of `ids` (method `search`).
Every model object needs to define a `DataLoader` for retrieving data.
At last, a GraphQL Server needs a `Context` object (in the exaple we use the
default context), which is responsable of parsing, query the correct data
loader and retrieving data given a client query through HTTP POST method.

Server side, a bunch of simple APIs are being provided, which once the server
is up and running, is possibile to see and to query visiting
http://localhost:8080/graphiql. This gui is a playground where it's possibile
to test the queries provided by the server.

Client side, things are made simple thanks to the GraphQL-Kotlin gradle plugin,
which auto generates the schema for the queries once they're declared in a
"*.graphql" query file under `/resources/` folder. Now thanks to Ktor and
OkHTTP, we can query the server (using also kotlin coroutines) (<- that is the
main reason why we are using graphql-koltin and ktor - they provide a great
support for coroutines) creating a simple HTTP POST request, where under the
hood, the correct payload is created for letting the server understand the
package we are sending.


#### Client
Queries works correcty, but subscription does not seem to work. A test with
[Apollo Kotlin](https://github.com/apollographql/apollo-kotlin) will be made,
and this will also lead to the discovery of Apollo that claims to simplify
GraphQL Client.
