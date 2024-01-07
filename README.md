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

好了做完上面三步然后同步下工程后buildSrc这个文件就变色了，除此之外main java目录也会变色。这里就可以创建文件来抽取依赖了：

```kotlin
/**
 * 项目中要使用的依赖
 * */
object Dependency {
    const val coreKtx = "androidx.core:core-ktx:1.9.0"
    const val appcompat = "androidx.appcompat:appcompat:1.6.1"
    const val material = "com.google.android.material:material:1.8.0"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.4"
}

object Test {
    const val junit = "junit:junit:4.13.2"
    const val ext = "androidx.test.ext:junit:1.1.5"
    const val espressoCore = "androidx.test.espresso:espresso-core:3.5.1"
}
```

然后app/build.gradle.kts 就可以这样引入了：

```kotlin
dependencies {
    implementation (Dependency.coreKtx)
    implementation (Dependency.appcompat)
    implementation (Dependency.material)
    implementation (Dependency.constraintlayout)
    testImplementation (Test.junit)
    androidTestImplementation (Test.ext)
    androidTestImplementation (Test.espressoCore)
}
```

其实app/build.gradle.kts 内的dependencies{} 这块还可以继续抽取，把这块单独放入一个gradle文件中然后通过apply from引入即可。


###### 二、Base类的设计

架构上采用MVI模式，结合了DataBinding。整体页面受数据驱动。 具体实现上使用了StateFlow、ShareFlow。

UI层通过dispatchEvent向VM发送event，VM处理event 然后更新数据。UI层通过StateFlow能监听到最新更新的数据，然后更新UI。

VM层也可以向UI发送action，UI层通过ShareFlow监听，处理对应的action。

整个过程设计数据遵循单向流动，不应做UI层直接从VM定义的方法中取数据。

```kotlin
sealed class LoadState {
    // 空数据状态，默认。
    data object EMPTY : LoadState()

    // 加载状态
    data object LOADING : LoadState()

    // 加载成功状态
    data object DATA : LoadState()

    //加载失败状态
    data object ERROR : LoadState()
}
```

```kotlin
/**
 * Create by SunnyDay /01/07 13:04:23
 * we can add other action when in need.
 */
sealed class ViewAction {
    data class DisplayScreen<T>(val screen: T) : ViewAction()

    data object CloseScreen : ViewAction()

   data class ShowToast(val msg: String) : ViewAction()

}
```

```kotlin
abstract class BaseViewModel<VS, VE>(initViewState: VS) : ViewModel() {
    private val _uiFlow = MutableStateFlow(initViewState)
    var uiFlow = _uiFlow

    private val _actionFlow = MutableSharedFlow<ViewAction>()
    var actionFlow = _actionFlow

    /**
     * 向ui层刷新最新的UI状态StateFlow的value发生改变时，末端操作符会收到值的变化。
     * */
    var mCurrentState = initViewState
        set(value) {
            field = value
            uiFlow.value = value
        }


    /**
     * 处理UI层发来的事件
     * */
    abstract fun onViewEvent(event: VE)

    /**
     * 向UI层发送action
     * */
    protected fun dispatchAction(action: ViewAction) {
        viewModelScope.launch {
            actionFlow.emit(action)
        }
    }
}
```

```kotlin
/**
 * Create by SunnyDay /01/07 12:49:36
 */
abstract class BaseActivity<VS, VE> : AppCompatActivity() {

    protected abstract val mBinding: ViewDataBinding
    protected abstract val mViewModel: BaseViewModel<VS, VE>
    final override fun  onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        lifecycleScope.launch {
            repeatOnLifecycle((Lifecycle.State.STARTED)) {
                launch {
                    mViewModel.uiFlow.collectLatest {
                        onViewStateUpdate(it)
                    }
                }
                launch {
                    mViewModel.actionFlow.collect {
                        when (it) {
                            is ViewAction.DisplayScreen<*> -> onDisplayScreenAction(it)
                            is ViewAction.CloseScreen -> finish()
                            is ViewAction.ShowToast -> showToast(it.msg)
                        }
                    }
                }
            }
        }
        initView()
    }

    /**
     * 做一些出初始化工作
     * */
    abstract fun initView()

    /**
     * 最新的UI数据
     * */
    abstract fun onViewStateUpdate(viewState: VS)


    /**
     * UI层收到action处理工作。这里主要处理DisplayScreen相关操作。如弹窗，跳页面。
     * */
    abstract fun onDisplayScreenAction(it: ViewAction.DisplayScreen<*>)


    /**
     * 每个viewModel实现类实现onViewEvent方法，处理Ui层发来的event
     * */
    fun dispatchEvent(event: VE) {
        mViewModel.onViewEvent(event)
    }

    private fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}
```

```kotlin

```

如上是BaseActivity、BaseView相关的抽取，接下来举个🌰看具体怎样使用的：

```kotlin
interface MainContract {

    abstract class ViewModel:BaseViewModel<ViewState,ViewEvent>(ViewState())

    data class ViewState(val loadState: LoadState = LoadState.EMPTY)

    sealed class ViewEvent{
        data object Init:ViewEvent()
    }
}
```
```kotlin
class MainViewModel : ViewModel() {
    override fun onViewEvent(event: MainContract.ViewEvent) {
        when (event) {
            is MainContract.ViewEvent.Init -> initData()
        }
    }

    /**
     * test
     * 1、UI layer -> dispatch event -> vm handle it
     * 2、vm -> dispatch view action -> ui layer receive listener
     * */
    private fun initData() {
        mCurrentState = mCurrentState.copy(loadState = LoadState.DATA)
        viewModelScope.launch {
            delay(1000)
            dispatchAction(ViewAction.ShowToast("i am a toast test"))
        }
    }
    
}
```

```kotlin
class MainActivity : BaseActivity<ViewState,ViewEvent>() {

    override val mBinding: ActivityMainBinding  by BindActivity(R.layout.activity_main)

    override val mViewModel  by viewModels<MainViewModel>()


    override fun initView() {
      mBinding.tvBtn.setOnClickListener {
          dispatchEvent(ViewEvent.Init)
      }
    }

    override fun onDisplayScreenAction(it: ViewAction.DisplayScreen<*>) {

    }

    override fun onViewStateUpdate(viewState: ViewState) {
        Timber.d("MainActivity-onViewStateUpdate:${viewState}")
    }
}
```

这里就举个activity的🌰，fragment的就不列举了，Base类以及实现类的实现都差不多~














