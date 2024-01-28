package com.example.better.core

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

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
                        Timber.d("BaseActivity mViewModel.uiFlow")
                        onViewStateUpdate(it)
                    }
                }
                launch {
                    /**
                     * 这里不用collectLatest，这里要是使用collectLatest存在先到的viewAction被后者取消的case。
                     * 理想状态下应该不存在这种case，我们这里无任何耗时操作，来一个这里就处理了一个。
                     */
                    mViewModel.actionFlow.collect {
                        Timber.d("BaseActivity mViewModel.actionFlow")
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