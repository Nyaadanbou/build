import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient
import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.time.Duration

abstract class CopyJarDockerTask : DefaultTask() {

    @get:Input
    abstract val dockerHost: Property<String>

    @get:Input
    abstract val containerId: Property<String>

    @get:Input
    abstract val containerPath: Property<String>

    @get:Input
    abstract val fileMode: Property<Int>

    @get:Input
    abstract val userId: Property<Int>

    @get:Input
    abstract val groupId: Property<Int>

    @get:InputFile
    @get:PathSensitive(PathSensitivity.NAME_ONLY)
    abstract val sourceFile: RegularFileProperty

    @TaskAction
    fun run() {
        validateInputs()
        copyToContainer()
    }

    private fun validateInputs() {
        require(!containerId.orNull.isNullOrBlank()) { "Property 'containerId' must be specified" }
        require(!containerPath.orNull.isNullOrBlank()) { "Property 'containerPath' must be specified" }
        require(sourceFile.get().asFile.exists()) { "Source file does not exist" }
    }

    private fun copyToContainer() {
        // 获取最终使用的 Docker Host
        val dockerHost = when (val host = dockerHost.get()) {
            "auto" -> autoDetectDockerHost()
            else -> host
        }

        // 验证协议格式
        require(dockerHost.matches(Regex("^(tcp|npipe|unix|http|https)://.*"))) {
            "Invalid Docker host protocol: $dockerHost"
        }

        val containerId = containerId.get()
        val containerPath = containerPath.get()
        val fileMode = fileMode.get()
        val userId = userId.get()
        val groupId = groupId.get()
        val source = sourceFile.get().asFile

        logger.lifecycle("Copying JAR file {} to container: {}:{}", source.name, containerId, containerPath)

        val tarOutputStream = ByteArrayOutputStream()
        TarArchiveOutputStream(tarOutputStream).use { tarOut ->
            tarOut.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU)
            val entry = TarArchiveEntry(source.name).apply {
                this.size = source.length()
                this.mode = fileMode
                this.userId = userId
                this.groupId = groupId
            }

            tarOut.putArchiveEntry(entry)
            source.inputStream().use { it.copyTo(tarOut) }
            tarOut.closeArchiveEntry()
        }

        val tarInputStream = ByteArrayInputStream(tarOutputStream.toByteArray())

        val dockerConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
            .withDockerHost(dockerHost)
            .build() // 这样的话, 仅支持本地 Docker Engine
        val httpClient = ApacheDockerHttpClient.Builder()
            .dockerHost(dockerConfig.dockerHost)
            .sslConfig(dockerConfig.sslConfig)
            .maxConnections(100)
            .connectionTimeout(Duration.ofSeconds(30))
            .responseTimeout(Duration.ofSeconds(45))
            .build()
        DockerClientImpl.getInstance(dockerConfig, httpClient).use { dockerClient ->
            try {
                dockerClient.copyArchiveToContainerCmd(containerId)
                    .withTarInputStream(tarInputStream)
                    .withRemotePath(containerPath)
                    .exec()
                logger.lifecycle("Successfully copied JAR file to container: {}:{}", containerId, containerPath)
            } catch (e: Exception) {
                logger.error("Failed to copy JAR file to container: {}:{}", containerId, containerPath, e)
                throw e
            }
        }
    }

    private fun autoDetectDockerHost(): String = when {
        System.getProperty("os.name").startsWith("windows", true) ->
            "npipe:////./pipe/docker_engine"

        else ->
            "unix:///var/run/docker.sock"
    }

}
