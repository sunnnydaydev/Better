package com.example.better.contracts

import com.example.better.core.BaseViewModel
import com.example.better.core.LoadState

/**
 * Create by SunnyDay /01/07 13:52:33
 */
interface MainContract {

    abstract class ViewModel:BaseViewModel<ViewState,ViewEvent>(ViewState())

    data class ViewState(val loadState: LoadState = LoadState.EMPTY)

    sealed class ViewEvent{
        data object Init:ViewEvent()
    }
}