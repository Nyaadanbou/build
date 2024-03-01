plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.caffeine) {
        exclude("org.checkerframework")
        exclude("com.google.errorprone")
    }
}

publishing {
    publications {
        create<MavenPublication>("caffeinePlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "caffeine"
            version = "1.0"
        }
    }
}