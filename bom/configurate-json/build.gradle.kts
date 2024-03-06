plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.configurate.json) {
        exclude("com.google.errorprone")
    }
}

publishing {
    publications {
        create<MavenPublication>("configuratePlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "configurate-json"
            version = "1.0"
        }
    }
}