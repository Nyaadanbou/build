plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.cloud2.kotlin.extensions)
    api(libs.cloud2.kotlin.coroutines)
}

publishing {
    publications {
        create<MavenPublication>("cloudKotlinPlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "cloud-kotlin"
            version = "1.0"
        }
    }
}