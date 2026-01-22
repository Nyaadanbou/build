import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.kotlin.dsl.withType

/**
 * Configures the given task to run after the copy task of all dependent projects.
 *
 * See copy-jar-build-plugin for rationale.
 */
fun Project.configureTaskDependencies(task: String) {
    val compileJava = tasks.matching { it.name == "compileJava" }
    if (compileJava.isEmpty()) return

    configurations.forEach { configuration ->
        configuration.dependencies.withType<ProjectDependency>().forEach { dep ->
            val dependencyProjectPath = dep.path
            val dependencyProject = rootProject.findProject(dependencyProjectPath) ?: return@forEach
            if (!dependencyProject.tasks.names.contains(task)) return@forEach

            val dependencyTaskPath = "$dependencyProjectPath:$task"
            compileJava.configureEach {
                mustRunAfter(dependencyTaskPath)
            }
        }
    }
}