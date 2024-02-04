package com.example.better.bindingmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.better.BR
import com.example.better.core.LoadState

/**
 * Create by Carry /02/04 18:06:59
 */
class HomeFragmentBindingModel : BaseObservable() {
    @Bindable
    var loadState = LoadState.EMPTY
        set(value) {
            field = value
            notifyPropertyChanged(BR.loadState)
        }

    /**
     * show main loading when loadState is LoadState.LOADING
     * */
    @get:Bindable("loadState")
    val showMainLoader: Boolean get() = loadState == LoadState.LOADING
}