package com.example.better.core

/**
 * Create by SunnyDay /01/07 13:42:41
 */
sealed class LoadState {
    // 空数据状态，默认。
    data object EMPTY : LoadState()

    // 加载状态
    data object LOADING : LoadState()

    // 加载成功状态
    data object DATA : LoadState()

    //加载失败状态
    data object ERROR : LoadState()
}