import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.kotlin.dsl.withType

/**
 * Configures the given task to run after the copy task of all dependent projects.
 *
 * @param task The name of the task which produces the file to be copied.
 */
fun Project.configureTaskDependencies(task: String) {
    configurations.forEach { configuration ->
        configuration.dependencies.withType<ProjectDependency>().forEach { dep ->
            val dependentProject = dep.dependencyProject
            val copyTask = dependentProject.tasks.findByName(task)
            if (copyTask != null) {
                tasks.named("compileJava") {
                    mustRunAfter(copyTask)
                }
            }
        }
    }
}