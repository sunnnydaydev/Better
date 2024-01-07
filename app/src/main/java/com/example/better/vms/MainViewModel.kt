package com.example.better.vms

import androidx.lifecycle.viewModelScope
import com.example.better.contracts.MainContract
import com.example.better.contracts.MainContract.ViewModel
import com.example.better.core.LoadState
import com.example.better.core.ViewAction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Create by SunnyDay /01/07 14:09:47
 */
class MainViewModel : ViewModel() {
    override fun onViewEvent(event: MainContract.ViewEvent) {
        when (event) {
            is MainContract.ViewEvent.Init -> initData()

            is MainContract.ViewEvent.Test ->    mCurrentState = mCurrentState.copy(loadState = LoadState.LOADING)
        }
    }

    /**
     * test
     * 1、UI layer -> dispatch event -> vm handle it
     * 2、vm -> dispatch view action -> ui layer receive listener
     * */
    private fun initData() {
        Timber.d("MainViewModel initData")
        mCurrentState = mCurrentState.copy(loadState = LoadState.DATA)

        viewModelScope.launch {
            delay(1000)
            dispatchAction(ViewAction.ShowToast("i am a toast test"))
        }
    }

}