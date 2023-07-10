# GraphQL Server
## Instructions
### Building
Build the project using

```bash
./gradlew clean build
```

This may take a while. If the operation has been completed succefully, you
can run the server with:
```bash
./gradlew run
```
*Note*: **port 8080** will be used by this server, so make sure it is not in use
before running.

### Playground
Once the server is up and running, you can visit
(GraphiQLPlayground)[http://localhost:8080/graphiql] to fetch the schema, and
test queries/mutation/subscriptions offered by the server.

