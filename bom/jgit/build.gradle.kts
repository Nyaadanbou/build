plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.jgit) {
        exclude("org.slf4j")
    }
}

publishing {
    publications {
        create<MavenPublication>("creativePlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "jgit"
            version = "0.1"
        }
    }
}