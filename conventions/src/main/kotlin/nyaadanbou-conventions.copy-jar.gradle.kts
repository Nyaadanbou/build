/**
 * ## `copyJar`
 *
 * 提供了一个新的任务 `build:copyJar`, 用于快速复制和部署输出的 JAR.
 *
 * ### 默认行为
 *
 * `copyJar` 任务会自动将构建生成的 JAR 文件复制到项目的 `build/` 目录下, 并按用户指定的文件名进行重命名.
 * - 如果项目启用了 `shadow` 插件, 任务会复制 `shadowJar` 任务的输出.
 * - 如果项目没有启用 `shadow` 插件, 则会复制 `jar` 任务的输出.
 *
 * ### 自定义配置
 *
 * 用户可以通过 `copyJar` 扩展来自定义输出 JAR 的文件名.
 * 默认情况下, 文件名格式为 `${project.name}-${project.version}.jar`.
 *
 * 例如:
 * ```kotlin
 * copyJar {
 *     jarName.set("my-custom-name.jar")
 * }
 * ```
 *
 * ### 本地路径配置
 *
 * 如果需要额外的复制到自定义路径, 可以在项目根目录创建 `copyjar.properties` 文件, 并在文件中配置以下属性:
 *
 * - `copyJar.copyPath`: 指定要复制的额外路径.
 * - `copyJar.syncPaths`: 一组路径（以逗号分隔）, 用于 `syncJar` 任务.
 *
 * 示例 `copyjar.properties` 文件内容:
 * ```
 * copyJar.copyPath=/path/to/custom/dir
 * copyJar.syncPaths=dev:server/plugins,test:backup/plugins
 * ```
 *
 * ### `syncJar` 任务
 *
 * `syncJar` 任务用于将生成的 JAR 文件同步到多个指定路径, 使用的是 `rsync` 命令, 支持远程服务器路径.
 * 用户可以在 `copyjar.properties` 文件中通过 `copyJar.syncPaths` 来配置这些路径.
 */

import java.nio.file.Path
import java.nio.file.Paths
import java.util.Properties

plugins {
    java
}

fun loadConfig(): Properties {
    val properties = Properties()
    val propertiesFile = file("copyjar.properties")

    if (propertiesFile.exists()) {
        propertiesFile.inputStream().use { properties.load(it) }
    }

    return properties
}

// 加载本地配置文件
val config = loadConfig()

// 获取本地属性值
val copyPath: Path? = config.getProperty("copyJar.copyPath")?.let { Paths.get(it) }
val syncPaths: List<String> = config.getProperty("copyJar.syncPaths")?.split(",") ?: emptyList()

// 允许用户自定义复制操作
interface CopyJarExtension {
    /**
     * 复制后的 JAR 文件名, 包括文件扩展名.
     *
     * 默认为 `${project.name}-${project.version}.jar`.
     */
    val jarName: Property<String>
}

// 创建扩展, 用于用户自定义配置
val copyJar = project.extensions.create<CopyJarExtension>("copyJar").apply {
    jarName.convention("${project.name}-${project.version}.jar")
}

tasks {
    register<Copy>("copyJar") {
        group = "build"

        duplicatesStrategy = DuplicatesStrategy.WARN
        doNotTrackState("Overwrites the plugin jar to allow for easier reloading")

        // 获取用户自定义的 JAR 文件名
        val jarName = copyJar.jarName.orNull ?: throw GradleException("`jarName` is required")

        // 获取构建输出的 JAR 文件（来自 `shadowJar` 或 `jar` 任务）
        val jarTask = findByName("shadowJar") ?: findByName("jar") ?: throw GradleException("No `shadowJar` or `jar` task found")

        // 默认复制到项目的 build 目录下
        val destFile = layout.buildDirectory

        from(jarTask)
        into(destFile)
        rename { jarName }

        doLast {
            logger.lifecycle("Copied to project build directory")
        }

        // 如果在 copyjar.properties 中指定了 copyJar.copyPath, 则复制到指定的路径
        if (copyPath != null) {
            doLast {
                copy {
                    duplicatesStrategy = DuplicatesStrategy.WARN
                    from(jarTask)
                    into(copyPath)
                    rename { jarName }
                    logger.lifecycle("Copied to extra custom directory: $copyPath")
                }
            }
        }
    }

    register<Task>("syncJar") {
        group = "build"

        doLast {
            if (syncPaths.isEmpty()) {
                logger.error("No sync path found, skipping syncing")
                return@doLast
            }

            val jarName = copyJar.jarName.orNull ?: throw GradleException("`jarName` is required")
            val jarFile = layout.buildDirectory.file(jarName).get().asFile

            if (!jarFile.exists()) {
                logger.error("The JAR file '${jarFile.path}' does not exist, skipping syncing")
                return@doLast
            }

            for (syncPath in syncPaths) {
                val args = arrayOf("rsync", "-avz", jarFile.path, syncPath)
                logger.lifecycle("Executing command: ${args.joinToString(" ")}")
                exec { commandLine(*args) }
            }
        }
    }
}