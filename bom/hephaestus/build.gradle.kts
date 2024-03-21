plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.hephaestus.core) {
        exclude("net.kyori")
        exclude("org.jetbrains", "annotations")
    }
    api(libs.hephaestus.reader.blockbench) {
        exclude("com.google.code.gson")
    }
    api(libs.hephaestus.runtime.bukkit.api)
    api(libs.hephaestus.runtime.bukkit.adapt) {
        targetConfiguration = "reobf"
    }
}

publishing {
    publications {
        create<MavenPublication>("creativePlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "hephaestus"
            version = "1.0"
        }
    }
}