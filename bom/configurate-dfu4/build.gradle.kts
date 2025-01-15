plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.configurate.extra.dfu4) {
        exclude("com.mojang")
        exclude("com.google.errorprone")
        exclude("xyz.xenondevs.configurate")
    }
}

publishing {
    publications {
        create<MavenPublication>("configuratePlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "configurate-extra-dfu4"
            version = "0.1"
        }
    }
}