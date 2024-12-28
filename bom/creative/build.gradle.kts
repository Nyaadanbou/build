plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.creative.api) {
        exclude("net.kyori")
        exclude("org.jetbrains", "annotations")
    }
    api(libs.creative.serializer.minecraft) {
        exclude("com.google.code.gson")
        exclude("net.kyori")
        exclude("team.unnamed", "creative-api")
    }
    api(libs.creative.server) {
        exclude("net.kyori")
        exclude("team.unnamed", "creative-api")
    }
}

publishing {
    publications {
        create<MavenPublication>("creativePlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "creative"
            version = "0.1"
        }
    }
}