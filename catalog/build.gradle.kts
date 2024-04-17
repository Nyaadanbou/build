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
            name = "mewcraftRepository"
            url = uri("https://repo.mewcraft.cc/private")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["versionCatalog"])
            group = "cc.mewcraft.gradle"
            artifactId = "catalog"
            version = "1.0"
            description = "Shared version catalogs"
        }
    }
}
