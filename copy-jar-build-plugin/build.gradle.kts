plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "cc.mewcraft.gradle"
version = "0.0.2-snapshot"

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
        create("CopyJarBuild") {
            id = "cc.mewcraft.copy-jar-build"
            implementationClass = "CopyJarBuildPlugin"
        }
    }
}

publishing {
    repositories {
        nyaadanbouPrivate()
    }
}