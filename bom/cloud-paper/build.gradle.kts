plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.cloud2.core) {
        exclude("org.jetbrains") // in paper runtime
    }
    api(libs.cloud2.paper) {
        exclude("org.jetbrains") // in paper runtime
        exclude("net.kyori") // in paper runtime
    }
    api(libs.cloud2.minecraft.extras) {
        exclude("org.jetbrains") // in paper runtime
        exclude("net.kyori") // in paper runtime
    }
}

publishing {
    publications {
        create<MavenPublication>("cloudPaperPlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "cloud-paper"
            version = "0.2"
        }
    }
}