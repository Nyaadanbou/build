plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.configurate.extra.dfu8) {
        exclude("com.mojang")
        exclude("com.google.errorprone")
        exclude("cc.mewcraft.configurate")
    }
}

publishing {
    publications {
        create<MavenPublication>("configuratePlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "configurate-extra-dfu8"
            version = "0.1"
        }
    }
}