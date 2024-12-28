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
        exclude("xyz.xenondevs.configurate")
    }
}

publishing {
    publications {
        create<MavenPublication>("configurateKotlinPlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "configurate-kotlin"
            version = "0.1"
        }
    }
}