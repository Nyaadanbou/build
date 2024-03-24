subprojects {
    apply(plugin = "maven-publish")

    configure<PublishingExtension> {
        repositories {
            maven(project.uri("${System.getProperty("user.home")}/MewcraftRepository"))
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