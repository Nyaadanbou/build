import org.gradle.api.Project

/**
 * Configures the given task to run after the copy task of all dependent projects.
 *
 * This is intentionally implemented without relying on internal/removed APIs like
 * `ProjectDependency.dependencyProject`. Instead, we look at the resolved dependency graph
 * and if a dependency corresponds to a project in this build (by path == ":<projectName>"),
 * we order our `compileJava` after that project's task.
 *
 * @param task The name of the task which produces the file to be copied.
 */
fun Project.configureTaskDependencies(task: String) {
    // We only need ordering; skipping missing tasks is fine.
    val compileJava = tasks.matching { it.name == "compileJava" }
    if (compileJava.isEmpty()) return

    configurations.forEach { configuration ->
        // Avoid forcing resolution for non-resolvable configurations.
        if (!configuration.isCanBeResolved) return@forEach

        val resolved = runCatching { configuration.incoming.resolutionResult }.getOrNull() ?: return@forEach

        // Find dependent projects by matching resolved module coordinates to project names.
        val dependentProjectPaths = buildSet {
            resolved.allComponents.forEach { component ->
                val module = component.moduleVersion
                // Only external/module components have moduleVersion; project components will have null here.
                val name = module?.name ?: return@forEach
                val path = ":$name"
                if (rootProject.findProject(path) != null) add(path)
            }
        }

        dependentProjectPaths.forEach { projectPath ->
            val dependencyTaskPath = "$projectPath:$task"
            compileJava.configureEach {
                // Gradle can wire task dependencies by path without realizing the other project.
                mustRunAfter(dependencyTaskPath)
            }
        }
    }
}