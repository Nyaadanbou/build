🔔 本插件可以将项目输出的 JAR 文件复制到目标 Docker 容器的特定路径.

### 应用插件

```kotlin
// file: build.gradle.kts

plugins {
    id("cc.mewcraft.copy-jar-docker") version "x.y.z" // 版本号请见本项目的 build.gradle.kts
}

dockerCopy {
    // 目标容器的 ID 和目标文件夹的路径
    // containerPath 以 `/` 打头以消歧义
    containerId = "your-container-id"
    containerPath = "your-container-path"

    // 本插件将会使用这些参数来设置目标文件的: 权限,用户,用户组
    fileMode = 0b110_100_100
    userId = 0
    groupId = 0

    // 产生 JAR 文件的 Gradle Task 的名字
    archiveTask = "jar"
}
```