tasks {
    register("deploy") {
        group = "mewcraft"
        dependsOn(gradle.includedBuild("bom").task(":publishAll"))
        dependsOn(gradle.includedBuild("catalog").task(":publish"))
        dependsOn(gradle.includedBuild("repositories").task(":publish"))
    }
}