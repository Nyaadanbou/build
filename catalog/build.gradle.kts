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
        maven(uri("${System.getProperty("user.home")}/MewcraftRepository"))
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
