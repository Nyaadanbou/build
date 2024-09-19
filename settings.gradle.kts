@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("catalog/libs.versions.toml"))
        }
    }
}

rootProject.name = "build"

includeBuild("bom")
includeBuild("catalog")
includeBuild("conventions")
includeBuild("repositories")
