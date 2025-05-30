plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.hephaestus.core) {
        exclude("net.kyori")
        exclude("org.jetbrains")
    }
    api(libs.hephaestus.reader.blockbench) {
        exclude("com.google.code.gson")
    }
    api(libs.hephaestus.runtime.bukkit.api) {
        exclude("com.google.code.gson")
        exclude("net.kyori")
        exclude("org.jetbrains")
    }
    api("${libs.hephaestus.runtime.bukkit.adapt.get()}:reobf")
}

publishing {
    publications {
        create<MavenPublication>("creativePlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "hephaestus"
            version = "0.1"
        }
    }
}