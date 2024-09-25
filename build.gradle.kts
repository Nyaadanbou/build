tasks {
    register("deploy") {
        group = "nyaadanbou"
        dependsOn(gradle.includedBuild("bom").task(":publishAll"))
        dependsOn(gradle.includedBuild("catalog").task(":publish"))
        dependsOn(gradle.includedBuild("conventions").task(":publish"))
    }
}