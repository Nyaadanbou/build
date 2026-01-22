import org.gradle.api.Project

/**
 * Configures the given task to run after the copy task of all dependent projects.
 *
 * See copy-jar-build-plugin for rationale.
 */
fun Project.configureTaskDependencies(task: String) {
    val compileJava = tasks.matching { it.name == "compileJava" }
    if (compileJava.isEmpty()) return

    configurations.forEach { configuration ->
        if (!configuration.isCanBeResolved) return@forEach
        val resolved = runCatching { configuration.incoming.resolutionResult }.getOrNull() ?: return@forEach

        val dependentProjectPaths = buildSet {
            resolved.allComponents.forEach { component ->
                val module = component.moduleVersion
                val name = module?.name ?: return@forEach
                val path = ":$name"
                if (rootProject.findProject(path) != null) add(path)
            }
        }

        dependentProjectPaths.forEach { projectPath ->
            val dependencyTaskPath = "$projectPath:$task"
            compileJava.configureEach {
                mustRunAfter(dependencyTaskPath)
            }
        }
    }
}