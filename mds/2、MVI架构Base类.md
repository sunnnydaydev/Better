# MVI 架构

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