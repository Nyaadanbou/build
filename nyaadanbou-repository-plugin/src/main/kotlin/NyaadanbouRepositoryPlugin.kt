import org.gradle.api.Plugin
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.PasswordCredentials
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.credentials
import java.net.URI

class NyaadanbouRepositoryPlugin : Plugin<Settings> {
    override fun apply(settings: Settings) {
        settings.applyRepositories()
    }
}

// 暴露这些扩展函数, 以便在需要显式声明仓库时使用, 例如 publishing {}

fun RepositoryHandler.nyaadanbouReleases() = maven {
    name = "nyaadanbouReleases"
    url = URI("https://repo.mewcraft.cc/releases")
}

fun RepositoryHandler.nyaadanbouSnapshots() = maven {
    name = "nyaadanbouSnapshots"
    url = URI("https://repo.mewcraft.cc/snapshots")
}

fun RepositoryHandler.nyaadanbouPrivate() = maven {
    name = "nyaadanbouPrivate"
    url = URI("https://repo.mewcraft.cc/private")
    credentials(PasswordCredentials::class) // 需要配置 gradle.properties 的凭据
}

private fun Settings.applyRepositories() {
    pluginManagement {
        repositories {
            mavenLocal() // 优先声明本地仓库
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

private fun RepositoryHandler.addNyaadanbouRepositories() {
    nyaadanbouReleases()
    nyaadanbouSnapshots() // 新增快照仓库
    nyaadanbouPrivate()
}