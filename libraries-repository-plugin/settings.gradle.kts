pluginManagement {
    // 优先从源码构建解析插件
    includeBuild("../nyaadanbou-repository-plugin")

    repositories {
        gradlePluginPortal()
        mavenLocal()
    }
}

plugins {
    id("nyaadanbou-repository") version "0.0.1-snapshot"
}
