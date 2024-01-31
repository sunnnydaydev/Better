package com.example.better.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Create by SunnyDay /01/09 21:03:24
 */
abstract class BaseFragment<VS, VE> : Fragment() {
    protected abstract val binding: ViewDataBinding
    protected abstract val viewModel: BaseViewModel<VS, VE>
    protected open val rootView: View
        get() = view ?: throw IllegalStateException("View not inflated yet")

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.d("my-test1")
        return binding.root
    }

    /**
     * as api said this method is invoked after onCreateView
     * */
    final override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("my-test2")
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiFlow.collectLatest {
                        onViewStateUpdate(it)
                    }
                }
                launch {
                    viewModel.actionFlow.collect {
                        when (it) {
                            is ViewAction.DisplayScreen<*> -> onDisplayScreenAction(it)
                            is ViewAction.CloseScreen -> findNavController().navigateUp()
                            is ViewAction.ShowToast -> showToast(it.msg)
                        }
                    }
                }
            }
        }
        initView()
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("my-test3")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("my-test4")
    }

    override fun onDestroyView() {
        (rootView.parent as? ViewGroup)?.endViewTransition(rootView)
        super.onDestroyView()
    }

    abstract fun initView()

    /**
     * 最新的UI数据
     * */
    abstract fun onViewStateUpdate(viewState: VS)

    /**
     * UI层收到action处理工作。这里主要处理DisplayScreen相关操作。如弹窗，跳页面。
     * */
    abstract fun onDisplayScreenAction(it: ViewAction.DisplayScreen<*>)

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 每个viewModel实现类实现onViewEvent方法，处理Ui层发来的event
     * */
    fun dispatchEvent(event: VE) {
        viewModel.onViewEvent(event)
    }
}