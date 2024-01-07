package com.example.better.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


/**
 * Create by SunnyDay /01/07 12:52:26
 */
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