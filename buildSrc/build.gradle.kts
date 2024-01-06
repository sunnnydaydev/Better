
/**
 * 引入Kotlin DSL 插件，它允许您使用 Kotlin 语言编写 Gradle 构建脚本。
 * kotlin-dsl 插件是使用 Kotlin 语言编写 Gradle 脚本的必需插件。
 * */
plugins {
    `kotlin-dsl`
}

/**
 * 配置构建脚本本身的依赖项。这里配置构建脚本使用的仓库。
 * */
buildscript {
    repositories {
//        maven { url = uri("https://maven.aliyun.com/repository/jcenter") }
//        maven { url = uri("https://maven.aliyun.com/repository/google") }
//        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
//        maven { url = uri("https://maven.aliyun.com/repository/public") }
        google()
        mavenCentral()
    }
}


/**
 * 配置gradle构建脚本使用的仓库
 * */
repositories {
//    maven("https://maven.aliyun.com/repository/jcenter")
//    maven("https://maven.aliyun.com/repository/google")
//    maven("https://maven.aliyun.com/repository/gradle-plugin")
//    maven("https://maven.aliyun.com/repository/public")
    google()
    mavenCentral()
    gradlePluginPortal()
}