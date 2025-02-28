plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "cc.mewcraft.gradle"
version = "0.0.1-snapshot"

tasks {
    named("build") {
        finalizedBy("publishToMavenLocal")
    }
}

java {
    withSourcesJar()
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("NyaadanbouRepository") {
            id = "nyaadanbou-repository"
            implementationClass = "NyaadanbouRepositoryPlugin"
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}