plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.cloud2.kotlin.coroutines) {
        exclude("org.jetbrains.kotlin")
        exclude("org.jetbrains.kotlinx")
    }
    api(libs.cloud2.kotlin.extensions) {
        exclude("org.jetbrains.kotlin")
        exclude("org.jetbrains.kotlinx")
    }
}

publishing {
    publications {
        create<MavenPublication>("cloudKotlinPlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "cloud-kotlin"
            version = "1.0-SNAPSHOT"
        }
    }
}