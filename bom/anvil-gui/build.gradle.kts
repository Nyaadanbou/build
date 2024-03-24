plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.anvilgui)
}

publishing {
    publications {
        create<MavenPublication>("anvilGuiPlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "anvil-gui"
            version = "1.0"
        }
    }
}