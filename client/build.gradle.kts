import com.expediagroup.graphql.plugin.gradle.graphql
import com.expediagroup.graphql.plugin.gradle.config.GraphQLSerializer

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val graphQLVersion: String by project

plugins {
    kotlin("jvm") version "1.8.22"
    id("io.ktor.plugin") version "2.3.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.22"
    id("com.expediagroup.graphql") version "6.5.3"

    application
}

application {
    mainClass.set("client.GraphQLClientApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions { jvmTarget = "17" }
}

repositories { mavenCentral() }

dependencies { 
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")

    implementation("com.expediagroup", "graphql-kotlin-client", graphQLVersion)
    implementation("com.expediagroup", "graphql-kotlin-ktor-client", "7.0.0-alpha.6")
}

tasks.withType<Test> { useJUnitPlatform() }

graphql {
    client {
        packageName = "generated"
        sdlEndpoint = "http://localhost:8080/sdl"
        serializer = GraphQLSerializer.KOTLINX
    }
}
