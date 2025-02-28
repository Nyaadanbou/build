import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.Copy

abstract class CopyJarBuildTask : Copy() {

    init {
        includeEmptyDirs = false
        duplicatesStrategy = DuplicatesStrategy.WARN
    }

}