# Apollo Client

## Instructions
Before building the project, the Apollo Clients needs the schema to be in the
correct folder. This folder is being specified inside the [`build.gradle.kts`](./build.gradle.kts)
file. The default location for the schema is `src/main/grahpql/`. You can manually
put inside this folder the schema, or you can donwload the schema from the 
**running** server by executing the command:
```bash
./gradlew downloadApolloSchema --endpoint='http://localhost:8080/graphql' \
--schema=./src/main/graphql/schema.graphqls
```
Where the *endpoint* is the address of the running server.

Once the schema has been placed in the correct location, you can build this
project by:
```bash
./gradlew clean build
```
And run the main application with:
```bash
./gradlew run
```

*Note:* the server must be up and running while donwloading the schema and
building the project.
