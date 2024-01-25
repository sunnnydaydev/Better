/**
 * 项目中要使用的依赖
 * */
object Dependency {
    const val coreKtx = "androidx.core:core-ktx:1.9.0"
    const val appcompat = "androidx.appcompat:appcompat:1.6.1"
    const val material = "com.google.android.material:material:1.8.0"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.4"

    //viewModel对协程扩展封装（viewModelScope）
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"

    // lifecycle对协程的扩展封装（lifeCycleScope）
    const val lifeCycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0"
    const val activityKtx = "androidx.activity:activity-ktx:1.5.0"

    // log lib
    const val timber = "com.jakewharton.timber:timber:4.7.1"

    //navigation
    private const val nav_version = "2.5.3"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$nav_version"
    const val navigationUI = "androidx.navigation:navigation-ui-ktx:$nav_version"

    // hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:2.48"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:2.48"

    // RecyclerView adapter lib
    const val reclaim = "com.github.fueled:reclaim:2.1.1"



}

object Test {
    const val junit = "junit:junit:4.13.2"
    const val ext = "androidx.test.ext:junit:1.1.5"
    const val espressoCore = "androidx.test.espresso:espresso-core:3.5.1"
}