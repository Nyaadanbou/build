plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.cloud2.processors.confirmation)
}

publishing {
    publications {
        create<MavenPublication>("cloudConfirmationPlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "cloud-confirmation"
            version = "1.0-SNAPSHOT"
        }
    }
}