@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven(uri("${System.getProperty("user.home")}/MewcraftRepository"))
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

dependencyResolutionManagement {
    repositories {
        maven(uri("${System.getProperty("user.home")}/MewcraftRepository"))
    }
    versionCatalogs {
        create("libs") {
            from(files("catalog/libs.versions.toml"))
        }
    }
}

rootProject.name = "build"

include(":bom")
file("bom").walkTopDown().maxDepth(1).drop(1).forEach {
    if (it.isDirectory && it.resolve("build.gradle.kts").exists()) {
        println("Including: ${it.name}")
        include("bom:${it.name}")
    }
}

includeBuild("catalog")
includeBuild("repositories")
