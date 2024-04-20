plugins {
    `kotlin-dsl`
    `maven-publish`
    `java-gradle-plugin`
}

group = "cc.mewcraft.gradle"
version = "1.1.1-SNAPSHOT"

gradlePlugin {
    plugins {
        create("repositories") {
            id = "neko.repositories"
            implementationClass = "cc.mewcraft.gradle.RepositoriesPlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "mewcraftRepository"
            url = uri("https://repo.mewcraft.cc/private")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
