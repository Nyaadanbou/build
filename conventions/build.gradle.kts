plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "cc.mewcraft.gradle"
version = "1.2"

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

publishing {
    repositories {
        maven {
            name = "nyaadanbou"
            url = uri("https://repo.mewcraft.cc/private")
            credentials {
                username = project.findProperty("nyaadanbou.mavenUsername") as String?
                password = project.findProperty("nyaadanbou.mavenPassword") as String?
            }
        }
    }
}