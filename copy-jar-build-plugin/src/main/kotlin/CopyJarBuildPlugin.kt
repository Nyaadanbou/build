import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.AbstractArchiveTask
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register

class CopyJarBuildPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create<CopyJarBuildExtension>("buildCopy")

        project.tasks.register<CopyJarBuildTask>("copyJarToBuild") {
            this.group = "nyaadanbou"
            doNotTrackState("archiveTask is optional")
        }

        project.afterEvaluate {
            val copyTask = project.tasks.named<CopyJarBuildTask>("copyJarToBuild").get()
            val archiveTaskName = extension.archiveTask.orNull?.takeIf { it.isNotBlank() }

            if (archiveTaskName != null) {
                val archiveTask = project.tasks.named<AbstractArchiveTask>(archiveTaskName)
                val fileName = extension.fileName.orNull?.takeIf { !it.isNullOrBlank() }
                    ?.let { project.provider { it } }
                    ?: archiveTask.flatMap { it.archiveFileName }

                copyTask.dependsOn(archiveTask)
                copyTask.from(archiveTask)
                copyTask.into(project.layout.buildDirectory)
                copyTask.rename { fileName.get() }
            } else {
                copyTask.onlyIf { false }
            }

            project.configureTaskDependencies("copyJarToBuild")
        }
    }

}