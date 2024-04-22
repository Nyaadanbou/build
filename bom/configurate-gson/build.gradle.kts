plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.configurate.gson) {
        exclude("com.google.code.gson")
        exclude("com.google.errorprone")
    }
}

publishing {
    publications {
        create<MavenPublication>("configuratePlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "configurate-gson"
            version = "1.0-SNAPSHOT"
        }
    }
}