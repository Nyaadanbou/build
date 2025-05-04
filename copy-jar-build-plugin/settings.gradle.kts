pluginManagement {
    // 从源码构建解析插件
    includeBuild("../nyaadanbou-repository-plugin")

    repositories {
        gradlePluginPortal()
    }
}

plugins {
    id("nyaadanbou-repository") version "0.0.3-snapshot"
}
