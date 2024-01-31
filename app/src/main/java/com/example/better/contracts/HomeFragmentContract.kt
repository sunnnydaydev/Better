package com.example.better.contracts

import com.example.better.core.BaseViewModel
import com.example.better.core.LoadState
import com.example.better.entity.HomeEntity

/**
 * Create by SunnyDay /01/22 21:36:53
 */
interface HomeFragmentContract {
    abstract class ViewModel : BaseViewModel<ViewState, ViewEvent>(ViewState())

    data class ViewState(
        val loadState: LoadState = LoadState.EMPTY,
        val homeEntity: HomeEntity? = null
    )

    sealed class ViewEvent {
        object Init : ViewEvent()
    }
}