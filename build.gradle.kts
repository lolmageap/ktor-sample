val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    id("io.ktor.plugin") version "2.3.3"
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.jpa") version "1.7.22"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-swagger-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("io.ktor:ktor-serialization-jackson:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.hibernate:hibernate-core:6.1.7.Final")
    implementation("org.hibernate:hibernate-entitymanager:5.6.15.Final")
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation(group = "com.linecorp.kotlin-jdsl", name = "spring-data-kotlin-jdsl-starter", version = "2.0.4.RELEASE")

    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation(group = "org.postgresql", name = "postgresql", version = "42.1.4")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

}
