val kotlinVersion: String by project
val logbackVersion: String by project
val apolloVersion: String by project

plugins {
    kotlin("jvm") version "1.8.22"
    id("com.apollographql.apollo3") version "4.0.0-alpha.2"
    application
}

application {
    mainClass.set("apollo.client.ClientApplicationKt")
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
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    implementation("com.apollographql.apollo3:apollo-runtime:$apolloVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging { events("passed", "skipped", "failed") }
}

apollo {
  service("service") {
    packageName.set("apollo.client")
  }
}
