plugins {
    `version-catalog`
    `maven-publish`
}

catalog {
    versionCatalog {
        from(files("libs.versions.toml"))
    }
}

publishing {
    repositories {
        maven {
            name = "nyaadanbou"
            url = uri("https://repo.mewcraft.cc/private")
            credentials {
                username = project.findProperty("nyaadanbou.mavenUsername") as String?
                password = project.findProperty("nyaadanbou.mavenPassword") as String?
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["versionCatalog"])
            group = "cc.mewcraft.gradle"
            artifactId = "catalog"
            version = "0.7-SNAPSHOT"
            description = "Shared version catalogs"
        }
    }
}
