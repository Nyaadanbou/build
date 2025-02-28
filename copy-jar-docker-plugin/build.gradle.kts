plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "cc.mewcraft.gradle"
version = "0.0.1-snapshot"

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

dependencies {
    implementation(libs.docker.java)
    implementation(libs.apache.commons.compress)
}

gradlePlugin {
    plugins {
        create("CopyJarDocker") {
            id = "cc.mewcraft.copy-jar-docker"
            implementationClass = "CopyJarDockerPlugin"
        }
    }
}

publishing {
    repositories {
        nyaadanbouPrivate()
    }
}