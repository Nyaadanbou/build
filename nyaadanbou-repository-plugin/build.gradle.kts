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
        // 兼容旧 id: 仍然指向 Settings 插件实现
        create("NyaadanbouRepository") {
            id = "nyaadanbou-repository"
            implementationClass = "NyaadanbouRepositoryPlugin"
        }
        // 新的 Settings 级插件 id
        create("NyaadanbouRepositoryForSettings") {
            id = "nyaadanbou-repository-settings"
            implementationClass = "NyaadanbouRepositoryForSettingsPlugin"
        }
        // 新的 Project 级插件 id
        create("NyaadanbouRepositoryForProject") {
            id = "nyaadanbou-repository-project"
            implementationClass = "NyaadanbouRepositoryForProjectPlugin"
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}