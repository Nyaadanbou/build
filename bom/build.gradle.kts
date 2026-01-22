subprojects {
    apply(plugin = "maven-publish")

    configure<PublishingExtension> {
        repositories {
            maven {
                name = "nyaadanbouPrivate"
                url = uri("https://repo.mewcraft.cc/private")
                credentials(PasswordCredentials::class)
            }
        }
    }
}

tasks {
    register("buildAll") {
        group = "nyaadanbou"
        description = "Build all subprojects."
        subprojects.forEach { subproject ->
            val customTask = subproject.tasks.findByName("build")
            if (customTask != null) {
                dependsOn(customTask)
            }
        }
    }
    register("publishAll") {
        group = "nyaadanbou"
        description = "Publish all subprojects."
        subprojects.forEach { subproject ->
            val customTask = subproject.tasks.findByName("publish")
            if (customTask != null) {
                dependsOn(customTask)
            }
        }
    }
}