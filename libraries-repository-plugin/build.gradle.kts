plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "cc.mewcraft.gradle"
version = "0.0.4-snapshot"

java {
    withSourcesJar()
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        create("LibrariesRepository") {
            id = "cc.mewcraft.libraries-repository"
            implementationClass = "LibrariesRepositoryPlugin"
        }
    }
}

publishing {
    repositories {
        nyaadanbouPrivate()
    }
}