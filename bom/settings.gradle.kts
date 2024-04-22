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
            from(files("../catalog/libs.versions.toml"))
        }
    }
}

rootProject.name = "bom"

rootProject.projectDir.walkTopDown().maxDepth(1).drop(1).forEach { file ->
    if (file.isDirectory && file.resolve("build.gradle.kts").exists()) {
        include(file.name).also { println("Included: ${file.name}") }
    }
}
