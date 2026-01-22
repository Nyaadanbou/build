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
            name = "nyaadanbouPrivate"
            url = uri("https://repo.mewcraft.cc/private")
            credentials(PasswordCredentials::class)
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["versionCatalog"])
            group = "cc.mewcraft.gradle"
            artifactId = "catalog"
            version = "0.11-SNAPSHOT"
            description = "Shared version catalogs"
        }
    }
}
