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