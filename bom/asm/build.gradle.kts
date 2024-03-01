plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(libs.asm)
    api(libs.asm.commons)
}

publishing {
    publications {
        create<MavenPublication>("asmPlatform") {
            from(components["javaPlatform"])
            groupId = "cc.mewcraft.bom"
            artifactId = "asm"
            version = "1.0"
        }
    }
}