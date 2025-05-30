plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.configurate.extra.kotlin) {
        exclude("org.jetbrains.kotlin")
        exclude("org.jetbrains.kotlinx")
        exclude("cc.mewcraft.configurate")
    }
}

publishing {
    publications {
        create<MavenPublication>("configurateKotlinPlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "configurate-extra-kotlin"
            version = "0.2"
        }
    }
}