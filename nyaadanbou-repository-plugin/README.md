⚠️ 此插件仅发布到本地 Maven 仓库, 需手动部署.

### 安装到本地

🔔 在您的构建脚本使用本插件之前, 您需要将本插件安装到本地仓库.

1. 克隆本仓库
2. 执行发布命令:

```bash
./gradlew :nyaadanbou-repository-plugin:publish
```

### 应用插件

🔔 本插件是应用于 [Settings](https://docs.gradle.org/current/javadoc/org/gradle/api/initialization/Settings.html) 的插件, 不是应用于 [Project](https://docs.gradle.org/current/javadoc/org/gradle/api/Project.html) 的插件. 也就是说您应该在
`settings.gradle.kts` 中应用本插件, 而不是在 `build.gradle.kts` 中应用.

在 `settings.gradle.kts` 中添加如下内容:

```kotlin
// file: settings.gradle.kts

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal() // 从本地仓库解析该插件
    }
}

plugins {
    id("nyaadanbou-repository") version "x.y.z" // 版本号请见本项目的 build.gradle.kts
}
```

应用成功后, 本插件提供如下效果:

1. `settings.gradle.kts`:
    1. 设置好 `pluginManagement` 以便能够从本地仓库解析到本插件.
    2. 设置好 `dependencyResolutionManagement` 以便能够从[远程仓库](https://repo.mewcraft.cc/)解析依赖.
2. `build.gradle.kts`:
    1. 可以直接使用 `nyaadanbouReleases()`, `nyaadanbouSnapshots()`, `nyaadanbouPrivate()` 来添加仓库. 也就是说,
    与 `settings.gradle.kts` 相关联的所有 `build.gradle.kts` 可以使用以下扩展函数来添加我们自己的仓库:

    ```kotlin
    repositories { // RepositoryHandler 的上下文
        nyaadanbouReleases()
        nyaadanbouSnapshots()
        nyaadanbouPrivate() // 要求先设置好凭据: `~/.gradle/gradle.properties`
    }
    ```

### 凭据配置

🔔 设置好这里的凭据后, 您就可以开始使用我们的私有仓库: `nyaadanbouPrivate()`.

在 `~/.gradle/gradle.properties` 中添加:

```properties
# 这里不提供真实的用户名和密码
nyaadanbouPrivateUsername=your_username
nyaadanbouPrivatePassword=your_password
```
