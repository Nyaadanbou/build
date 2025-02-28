import org.gradle.api.provider.Property

/**
 * Configures docker-copy parameters.
 *
 * @property dockerHost Docker host (default: auto)
 * @property containerId Target Docker container ID (required)
 * @property containerPath Destination path in container (default: /)
 * @property fileMode File mode (default: 0b110_100_100)
 * @property userId User ID (default: 0)
 * @property groupId Group ID (default: 0)
 * @property archiveTask Name of task producing the archive (default: jar)
 */
abstract class CopyJarDockerExtension {

    abstract val dockerHost: Property<String> // auto | <URI>
    abstract val containerId: Property<String>
    abstract val containerPath: Property<String>
    abstract val fileMode: Property<Int>
    abstract val userId: Property<Int>
    abstract val groupId: Property<Int>
    abstract val archiveTask: Property<String>

    init {
        dockerHost.convention("auto")
        fileMode.convention(0b110_100_100)
        userId.convention(0)
        groupId.convention(0)
    }

}