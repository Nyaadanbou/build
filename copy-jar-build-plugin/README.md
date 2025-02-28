🔔 本插件可以将项目输出的 JAR 文件经过重命名复制到 `{project.root}/build` 目录下.

### 应用插件

```kotlin
// file: build.gradle.kts

plugins {
    id("cc.mewcraft.copy-jar-build") version "x.y.z" // 版本号请见本项目的 build.gradle.kts
}

buildCopy {
    // JAR 文件最终的名字
    fileName = "example-${project.version}.jar"

    // 产生 JAR 文件的 Gradle Task 的名字
    archiveTask = "shadowJar"
}
```