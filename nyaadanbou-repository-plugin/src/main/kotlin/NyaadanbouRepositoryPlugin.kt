import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.artifacts.repositories.PasswordCredentials
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.credentials
import org.gradle.kotlin.dsl.repositories
import java.net.URI

// 如需兼容旧 id, 可以保留原类名并委托到新的 Settings 插件实现
class NyaadanbouRepositoryPlugin : Plugin<Settings> {
    override fun apply(settings: Settings) {
        settings.applyNyaadanbouRepositoriesToSettings()
    }
}

// Settings 级插件: 配置 pluginManagement 和 dependencyResolutionManagement 的仓库
class NyaadanbouRepositoryForSettingsPlugin : Plugin<Settings> {
    override fun apply(settings: Settings) {
        settings.applyNyaadanbouRepositoriesToSettings()
    }
}

// Project 级插件: 为当前项目的 repositories 添加 Nyaadanbou 仓库
class NyaadanbouRepositoryForProjectPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.repositories {
            addNyaadanbouRepositories()
        }
    }
}

// 暴露这些扩展函数, 以便在需要显式声明仓库时使用, 例如 publishing {}

fun RepositoryHandler.nyaadanbouReleases(action: MavenArtifactRepository.() -> Unit = {}): MavenArtifactRepository {
    return maven {
        name = "nyaadanbouReleases"
        url = URI("https://repo.mewcraft.cc/releases")
        credentials(PasswordCredentials::class) // 需要配置 gradle.properties 的凭据
        action(this)
    }
}

fun RepositoryHandler.nyaadanbouSnapshots(action: MavenArtifactRepository.() -> Unit = {}): MavenArtifactRepository {
    return maven {
        name = "nyaadanbouSnapshots"
        url = URI("https://repo.mewcraft.cc/snapshots")
        credentials(PasswordCredentials::class) // 需要配置 gradle.properties 的凭据
        action(this)
    }
}

fun RepositoryHandler.nyaadanbouPrivate(action: MavenArtifactRepository.() -> Unit = {}): MavenArtifactRepository {
    return maven {
        name = "nyaadanbouPrivate"
        url = URI("https://repo.mewcraft.cc/private")
        credentials(PasswordCredentials::class) // 需要配置 gradle.properties 的凭据
        action(this)
    }
}

// Settings 专用: 统一注入 pluginManagement / dependencyResolutionManagement
internal fun Settings.applyNyaadanbouRepositoriesToSettings() {
    pluginManagement {
        repositories {
            // 优先声明本地仓库
            mavenLocal()

            // 官方 Gradle 插件仓库
            gradlePluginPortal()

            // 默认添加如下仓库来解析 gradle plugins
            addNyaadanbouRepositories()
        }
    }

    dependencyResolutionManagement {
        repositories {
            // 默认添加如下仓库来解析项目的依赖
            addNyaadanbouRepositories()
        }
    }
}

// 通用逻辑: 给 RepositoryHandler 加上默认仓库列表
internal fun RepositoryHandler.addNyaadanbouRepositories() {
    nyaadanbouReleases()
    nyaadanbouSnapshots() // 新增快照仓库
    nyaadanbouPrivate()
}