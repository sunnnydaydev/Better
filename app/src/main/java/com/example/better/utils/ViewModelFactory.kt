package com.example.better.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Create by SunnyDay /01/07 14:06:03
 *
 *ViewModel工具类，传递一个ViewModel实例，方法返回对应的实例。结合viewModels使用。
 *优雅的方案是使用依赖注入。
 */
class ViewModelFactory<T>(private val viewModel: T) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModel as T
    }
}