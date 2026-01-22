import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.kotlin.dsl.withType

/**
 * Configures the given task to run after the copy task of all dependent projects.
 *
 * ## Design constraints
 * - Must not resolve configurations (to avoid locking configuration hierarchies in Gradle 8/9).
 * - Must not rely on removed APIs like `ProjectDependency.dependencyProject`.
 * - Only needs to establish *ordering* (mustRunAfter) between tasks across projects.
 *
 * Strategy: use the *declared* `ProjectDependency.path` (e.g. ":core") and reference the
 * target task by its fully-qualified path (e.g. ":core:copyJarToBuild").
 *
 * @param task The name of the task which produces the file to be copied.
 */
fun Project.configureTaskDependencies(task: String) {
    // Only for JVM projects that actually have compileJava.
    val compileJava = tasks.matching { it.name == "compileJava" }
    if (compileJava.isEmpty()) return

    // Collect declared dependent project paths WITHOUT resolving any configuration.
    val dependentProjectPaths = linkedSetOf<String>()

    configurations.forEach { configuration ->
        configuration.dependencies.withType<ProjectDependency>().forEach { dep ->
            dependentProjectPaths += dep.path
        }
    }

    if (dependentProjectPaths.isEmpty()) return

    // Defer checking task existence until all projects are evaluated, so we don't accidentally
    // force early task discovery/configuration in other projects.
    gradle.projectsEvaluated {
        dependentProjectPaths.forEach { dependencyProjectPath ->
            val dependencyProject = rootProject.findProject(dependencyProjectPath) ?: return@forEach

            // If the dependent project doesn't have the task, just ignore (no hard failure).
            if (dependencyProject.tasks.findByName(task) == null) return@forEach

            val dependencyTaskPath = "$dependencyProjectPath:$task"
            compileJava.configureEach {
                mustRunAfter(dependencyTaskPath)
            }
        }
    }
}