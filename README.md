# Better

###### 1、名字由来

关于project的名字思索半天取了这个，寓意通过本次的综合练习技术能获得一些收获，也同时期望一切都越来越好！

###### 2、Project

为啥要写这个project呢？近一年来通过自学或者公司的项目了解到了一些jetpack的知识，想借此来练习下，综合运用下。

###### 3、技术：

- MVI架构
- Hilt
- Flow
- coroutines
- okHttp+retrofit
- glide
- DataBinding
- Navigation
- RecyclerView Item 三方库
- 待续...

# 设计

###### 一、buildSrc依赖

为了方便，把所有模块需要的依赖放入buildScr中，这样做的好处是buildScr会被gradle自动管理，每个模块都可使用这里的数据。

这样相对创建一个gradle文件然后每个模块apply的方式省事了很多。

在此我们需做如下几点,然后同步下工程即可，这样这个buildSrc就被本地自动管理了

(1) 在root工程下创建buildSrc目录

(2) buildSrc下创建[build.gradle.kts](./buildSrc/build.gradle.kts) 文件以及简单的配置

(3) buildSrc下创建src/main/java

其实这是gradle插件创建方式之一所需要的步骤，这里我们并不需要创建plugin因此做到这里就能满足我们的需求了。








