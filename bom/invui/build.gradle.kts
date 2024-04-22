plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.invui.core) {
        exclude("org.jetbrains")
    }
    api(libs.invui.extra.kotlin) {
        exclude("org.jetbrains.kotlin")
    }
    api(libs.invui.invaccess)
    runtime(libs.invui.invaccess.nms)
}

publishing {
    publications {
        create<MavenPublication>("invUiPlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "invui"
            version = "1.0-SNAPSHOT"
        }
    }
}
