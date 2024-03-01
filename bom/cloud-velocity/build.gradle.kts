plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    val version = "2.0.0-beta.2"
    api(libs.cloud2.core) {
        exclude("org.jetbrains")
    }
    api(libs.cloud2.velocity) {
        exclude("org.jetbrains")
        exclude("net.kyori")
    }
    api(libs.cloud2.minecraft.extras) {
        exclude("org.jetbrains")
        exclude("net.kyori")
    }
}

publishing {
    publications {
        create<MavenPublication>("cloudVelocityPlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "cloud-velocity"
            version = "1.0"
        }
    }
}