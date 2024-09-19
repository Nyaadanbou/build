/**
 * 本脚本提供了一个新的任务 `build:copyJar`, 用于快速复制和部署输出的 JAR.
 *
 * 默认情况下, 本脚本会把位于 `build/libs` 下的 JAR 复制到 `build` 下.
 * 如果项目启用了 shadow 插件, 那么会复制 `shadowJar` 任务的输出.
 * 如果项目没启用 shadow 插件, 那么会复制 `jar` 任务的输出.
 *
 * 你可以配置 `copyJar` 任务, 修改复制的目标路径和复制后的 JAR 文件名.
 *
 * 你还可以在项目的根目录下创建一个 `copyjar.txt` 文件, 其内容为绝对路径, 这样会*额外*复制到指定目录.
 */

import java.io.File

plugins {
    java
}

// 从文件中读取插件路径
val pluginPath: String? = layout.projectDirectory.file("copyjar.txt").asFile.takeIf { it.exists() }?.readText()?.trim()

// 允许用户自定义复制操作
interface CopyJarExtension {
    /**
     * 复制操作的目标路径, 如果要自定义最好写绝对路径.
     *
     * 默认为 `build`.
     */
    val destPath: Property<String>

    /**
     * 复制后的 JAR 文件名, 包括文件扩展名.
     *
     * 默认为 `${project.name}-${project.version}.jar`.
     */
    val jarName: Property<String>
}

// 允许用户自定义复制操作
val copyJar = project.extensions.create<CopyJarExtension>("copyJar")

tasks {
    register<Copy>("copyJar") {
        group = "build"

        doNotTrackState("Overwrites the plugin jar to allow for easier reloading")

        // Default copy to the current project's build directory
        val defaultDest = layout.buildDirectory.map { it.asFile.path }.get()

        val dest = copyJar.destPath.orNull ?: defaultDest
        val jarName = copyJar.jarName.orNull ?: "${project.name}-${project.version}.jar"

        // Always copy to the project's build directory
        from(findByName("shadowJar") ?: findByName("jar"))
        into(dest)
        doLast {
            println("Copied to project build directory: $dest")
        }
        rename(".*\\.jar", jarName)

        // If pluginPath is specified, copy there as well
        if (pluginPath != null) {
            doLast {
                val pluginDest = File(pluginPath)
                copy {
                    from(File(dest, jarName))
                    into(pluginDest)
                    println("Copied to extra custom directory: $pluginPath")
                }
            }
        }
    }
}
