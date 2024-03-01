subprojects {
    apply(plugin = "maven-publish")

    configure<PublishingExtension> {
        repositories {
            maven(project.uri("${System.getProperty("user.home")}/MewcraftRepository"))
        }
    }
}