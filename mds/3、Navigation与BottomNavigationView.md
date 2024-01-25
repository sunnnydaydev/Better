
# Navigation&BottomNavigationView

###### 1、tab对应导航图

当每个tab都对应一份导航图时，menu的item中id属性值要与对应导航图id的值保持一致

###### 2、BottomNavigationView属性的一些收获

```xml
        <!--
        1、可通过itemIconTint设置颜色selector来控制选中非选中的icon tint 颜色
        2、可通过itemTextColor设置颜色selector来控制选中非选中的text 颜色
        3、style的labelVisibilityMode 可控制label的mode
        4、BottomNavigationView 默认情况下选中状态图片tint为colorPrimary的颜色？
        不支持显示原图片的颜色图吗？
        -->
        <com.google.android.material.bottomnavigation.BottomNavigationView
            android:id="@+id/bottomNavigationView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_constraintTop_toBottomOf="@id/container"
            app:menu="@menu/bottom_nav_menu"
            app:itemIconTint="@color/select_bottom_icon_bg"
            app:itemTextColor="@color/select_bottom_icon_bg"
            style="@style/AppTheme.BottomNavigationView"/>
```

###### 3、二者的联动

```kotlin
 bottomNavigationView.setupWithNavController(getNavController())

private fun getNavController() =
    (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).navController
```

代码很简单，在搞这个时自己碰到了一个crash的坑，瞅了半天：

```xml
        <androidx.fragment.app.FragmentContainerView
            android:id="@+id/container"
            android:name="androidx.navigation.fragment.NavHostFragment"
            app:defaultNavHost="true"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight = "1"
            app:navGraph="@navigation/nav_graph"
            />
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/nav_graph"
    app:startDestination="@+id/nav_home">

    <!-- 发现个坑，graph内的startDestination 一定要用@+的方式引用方式如nav_home内：
    app:startDestination="@+id/fragment_home"
    若是这种写法：频繁切换几次tab必crash
    @id/fragment_home
    -->

    <include app:graph="@navigation/nav_home" />
    <include app:graph="@navigation/nav_video" />
    <include app:graph="@navigation/nav_cart" />
    <include app:graph="@navigation/nav_profile" />

</navigation>
```


###### 4、导航图的copy优化

我们可能会碰到一个问题，假如想要从fragmentA 跳转到fragmentB，那么fragmentB一定要注册到fragmentA 所在的导航图中。

这就产生了一些列的重复步骤，我们可能需要把新创建的fragmentX注册到每一份导航图中。此时注册一份，然后重复copy是一种方案，但是我们有
自动化的方案，gradle 编译期自动操作。



###### 5、参考

官方提供了多任务栈的实现sample具体可参考官方code

(NavigationAdvancedSample)(https://github.com/android/architecture-components-samples/tree/master/NavigationAdvancedSample)