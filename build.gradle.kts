tasks {
    register("deployAll") {
        group = "nyaadanbou"
        dependsOn(gradle.includedBuild("bom").task(":publishAll"))
        dependsOn(gradle.includedBuild("catalog").task(":publish"))
        dependsOn(gradle.includedBuild("conventions").task(":publish"))
        dependsOn(gradle.includedBuild("nyaadanbou-repository-plugin").task(":publishToMavenLocal"))
        dependsOn(gradle.includedBuild("libraries-repository-plugin").task(":publish"))
        dependsOn(gradle.includedBuild("copy-jar-build-plugin").task(":publish"))
        dependsOn(gradle.includedBuild("copy-jar-docker-plugin").task(":publish"))
    }
    register("buildAll") {
        group = "nyaadanbou"
        dependsOn(gradle.includedBuild("bom").task(":buildAll"))
        dependsOn(gradle.includedBuild("catalog").task(":generateCatalogAsToml"))
        dependsOn(gradle.includedBuild("conventions").task(":build"))
        dependsOn(gradle.includedBuild("nyaadanbou-repository-plugin").task(":publishToMavenLocal"))
        dependsOn(gradle.includedBuild("libraries-repository-plugin").task(":build"))
        dependsOn(gradle.includedBuild("copy-jar-build-plugin").task(":build"))
        dependsOn(gradle.includedBuild("copy-jar-docker-plugin").task(":build"))
    }
}