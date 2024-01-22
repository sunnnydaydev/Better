package com.example.better.contracts

import com.example.better.core.BaseViewModel
import com.example.better.core.LoadState

/**
 * Create by SunnyDay /01/22 21:36:53
 */
interface HomeFragmentContract {
    abstract class ViewModel : BaseViewModel<ViewState, ViewEvent>(ViewState())

    class ViewState(val loadState: LoadState = LoadState.EMPTY)

    sealed class ViewEvent {

    }
}