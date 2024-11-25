/**
 * ## `copyJar`
 *
 * 提供了一个新的任务 `build:copyJar`, 用于复制和部署构建生成的 JAR 文件.
 *
 * ### 默认行为
 *
 * `copyJar` 任务会自动将构建生成的 JAR 文件复制到项目的 `build/` 目录下, 并按用户指定的文件名进行重命名.
 * - 如果项目启用了 `paperweight` 插件, 任务会复制 `reobfJar` 任务的输出.
 * - 如果项目启用了 `shadow` 插件, 任务会复制 `shadowJar` 任务的输出.
 * - 如果项目没有启用 `shadow` 插件, 则会复制 `jar` 任务的输出.
 *
 * ### 配置
 *
 * 用户可以通过 `copyJar` 扩展来自定义输出 JAR 的文件名和目标平台.
 * 默认情况下, 文件名格式为 `${project.name}-${project.version}.jar`.
 *
 * 必须指定 `env` 属性, 用于选择目标平台.
 *
 * 示例配置：
 * ```kotlin
 * copyJar {
 *   environment.set("paper")
 *   jarFileName.set("my-custom-name.jar")
 * }
 * ```
 *
 * ### `gradle.properties` 文件配置
 *
 * 每个平台的复制路径和同步路径都需要在系统的 `gradle.properties` 文件中进行配置. 即使某个平台未使用, 也必须为其提供路径配置.
 *
 * - `copyJar.copyPath.{env}`: 指定 {env} 平台的复制路径.
 * - `copyJar.syncPath.{env}`: 指定 {env} 平台的同步路径列表, 多个路径以逗号分隔.
 *
 * 示例 `gradle.properties` 文件内容：
 * ```
 * copyJar.copyPath.{env}=/path/to/xyz
 * copyJar.syncPath.{env}=dev:data/xyz/plugins,test:backup/xyz/plugins
 * ```
 *
 * ### `syncJar` 任务
 *
 * `syncJar` 任务用于将生成的 JAR 文件同步到多个指定路径, 使用的是 `rsync` 命令, 支持远程服务器路径.
 * 用户可以通过 `gradle.properties` 文件中的 `copyJar.syncPath.{type}` 来配置这些路径.
 *
 * `syncJar` 任务会根据 `env` 确定使用哪个平台的配置.
 * 该任务会将 JAR 文件同步到配置文件中指定的路径列表中.
 */


import java.nio.file.Path
import java.nio.file.Paths

plugins {
    java
}

data class CopyConfig(
    val env: String,
    val copyPath: Path?,
    val syncPaths: List<String>,
)

fun getCopyConfig(project: Project, env: String): CopyConfig {
    val copyPath = project.findProperty("copyJar.copyPath.$env")?.toString()?.let { Paths.get(it) }
    val syncPathList = project.findProperty("copyJar.syncPath.$env")?.toString()?.split(",") ?: emptyList()
    return CopyConfig(env, copyPath, syncPathList)
}

// 允许用户自定义复制操作
interface CopyJarExtension {
    /**
     * 目标平台的种类.
     */
    val environment: Property<String>

    /**
     * 产生 JAR 文件的任务.
     *
     * 默认会首先寻找 `shadowJar` 任务, 如果没有则寻找 `jar` 任务.
     */
    val jarTaskName: Property<String>

    /**
     * 复制后的 JAR 文件名, 包括文件扩展名.
     *
     * 默认为 `${project.name}-${project.version}.jar`.
     */
    val jarFileName: Property<String>
}

// 创建扩展, 用于用户自定义配置
val copyJar = project.extensions.create<CopyJarExtension>("copyJar").apply {
    jarFileName.convention("${project.name}-${project.version}.jar")
}

tasks {
    register<Copy>("copyJar") {
        group = "build"
        duplicatesStrategy = DuplicatesStrategy.WARN
        doNotTrackState("Overwrites the plugin jar to allow for easier reloading")

        // 获取平台的类型
        val environment = copyJar.environment.orNull ?: throw GradleException("`environment` is required")

        // 获取当前平台的配置
        val copyConfig = getCopyConfig(project, environment)

        // 获取用户自定义的 JAR 文件名
        val jarName = copyJar.jarFileName.orNull ?: throw GradleException("`jarFileName` is required")

        // 获取构建输出的 JAR 文件（来自 `shadowJar` 或 `jar` 任务）
        val jarTask = copyJar.jarTaskName.orNull?.let(::findByName)
            ?: findByName("shadowJar")
            ?: findByName("jar")
            ?: throw GradleException("No `shadowJar` or `jar` task found")

        // 默认复制到项目的 build 目录下
        val destFile = layout.buildDirectory

        from(jarTask)
        into(destFile)
        rename { jarName }

        doLast {
            logger.lifecycle("Copied to project build directory")
        }

        // 如果 gradle.properties 中指定了平台对应的 copyPath, 则复制到指定的路径
        copyConfig.copyPath?.let { path ->
            doLast {
                copy {
                    duplicatesStrategy = DuplicatesStrategy.WARN
                    from(jarTask)
                    into(path)
                    rename { jarName }
                    logger.lifecycle("Copied to extra custom directory: $path")
                }
            }
        }
    }

    register<Task>("syncJar") {
        group = "build"
        doNotTrackState("Syncs the plugin jar to multiple paths")

        // 获取平台的类型
        val environment = copyJar.environment.orNull ?: throw GradleException("`environment` is required")

        // 获取当前平台的配置
        val copyConfig = getCopyConfig(project, environment)

        doLast {
            if (copyConfig.syncPaths.isEmpty()) {
                logger.error("No sync path found for copy type '${copyConfig.env}', skipping syncing")
                return@doLast
            }

            val jarName = copyJar.jarFileName.orNull ?: throw GradleException("`jarFileName` is required")
            val jarFile = layout.buildDirectory.file(jarName).get().asFile

            if (!jarFile.exists()) {
                logger.error("The JAR file '${jarFile.path}' does not exist, skipping syncing")
                return@doLast
            }

            for (syncPath in copyConfig.syncPaths) {
                val args = arrayOf("rsync", "-avz", jarFile.path, syncPath)
                logger.lifecycle("Executing command: ${args.joinToString(" ")}")
                exec { commandLine(*args) }
            }
        }
    }
}
