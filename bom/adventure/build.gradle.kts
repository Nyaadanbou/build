plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.adventure.nbt) {
        exclude("net.kyori")
    }
    api(libs.adventure.extra.kotlin) {
        exclude("net.kyori")
        exclude("org.jetbrains.kotlin")
    }
    api(libs.adventure.text.serializer.ansi) {
        exclude("net.kyori")
    }
}

publishing {
    publications {
        create<MavenPublication>("adventurePlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "adventure"
            version = "1.0-SNAPSHOT"
        }
    }
}