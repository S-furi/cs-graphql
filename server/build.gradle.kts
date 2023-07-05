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
    mainClass.set("server.GraphQLServerApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies { 
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

    implementation("com.expediagroup", "graphql-kotlin-server", graphQLVersion)
    implementation("com.expediagroup", "graphql-kotlin-ktor-server", "7.0.0-alpha.6")

}

repositories {
    mavenCentral()
}

tasks.withType<Test> {
    useJUnitPlatform()
}
