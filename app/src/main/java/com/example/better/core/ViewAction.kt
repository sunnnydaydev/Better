package com.example.better.core

/**
 * Create by SunnyDay /01/07 13:04:23
 */
sealed class ViewAction {
    data class DisplayScreen<T>(val screen: T) : ViewAction()

    data object CloseScreen : ViewAction()

   data class ShowToast(val msg: String) : ViewAction()

}