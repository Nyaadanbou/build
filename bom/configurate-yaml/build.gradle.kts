plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.configurate.yaml) {
        exclude("com.google.errorprone")
    }
}

publishing {
    publications {
        create<MavenPublication>("configuratePlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "configurate-yaml"
            version = "1.0"
        }
    }
}