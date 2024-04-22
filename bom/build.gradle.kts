subprojects {
    apply(plugin = "maven-publish")

    configure<PublishingExtension> {
        repositories {
            maven {
                name = "nyaadanbou"
                url = uri("https://repo.mewcraft.cc/private")
                credentials(PasswordCredentials::class)
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
    }
}

tasks {
    register("publishAll") {
        group = "mewcraft"
        description = "Publish all subprojects."
        subprojects.forEach { subproject ->
            val customTask = subproject.tasks.findByName("publish")
            if (customTask != null) {
                dependsOn(customTask)
            }
        }
    }
}