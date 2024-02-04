package com.example.better.core

/**
 * Create by SunnyDay /01/07 13:42:41
 */
enum class LoadState {
    // 空数据状态，默认。
    EMPTY,

    // 加载状态
    LOADING,

    // 加载成功状态
    DATA,

    //加载失败状态
    ERROR,
}