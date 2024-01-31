package com.example.better.vms

import androidx.lifecycle.viewModelScope
import com.example.better.contracts.HomeFragmentContract
import com.example.better.core.LoadState
import com.example.better.entity.HomeEntity
import com.example.better.helper.SerializationHelper
import com.example.better.repo.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import timber.log.Timber
import javax.inject.Inject

/**
 * Create by SunnyDay /01/22 21:40:59
 */

@ExperimentalSerializationApi
@HiltViewModel
class HomeFragmentViewModel @Inject constructor() : HomeFragmentContract.ViewModel() {

    @Inject
    lateinit var repository: HomeRepository

    init {
        /**
         *  如下直接打log这样会crash：
         *  Timber.d("HomeFragmentViewModel->${repository.localDataSource.getLocalData()}")
         *  如何避免crash呢，有如下几种方式：
         *  1、delay后使用
         *  2、通过构造注入也不会crash。
         *  结论：Hilt setter注入的字段 不能直接通过init调用。
         */
        viewModelScope.launch {
          //  Timber.d("HomeFragmentViewModel->${repository.localDataSource.getLocalData()}")
        }
    }

    override fun onViewEvent(event: HomeFragmentContract.ViewEvent) {
        Timber.d("HomeFragmentViewModel->${repository.localDataSource.getLocalData()}")
        when(event){
            is HomeFragmentContract.ViewEvent.Init -> initData()
            else -> {}
        }
    }



    private fun initData() {
        val homeEntity = SerializationHelper.serialization.decodeFromString<HomeEntity>(repository.localDataSource.getLocalData())
        repository.localDataSource
        viewModelScope.launch {
            mCurrentState = mCurrentState.copy(loadState = LoadState.LOADING)
            delay(2000)
            mCurrentState = mCurrentState.copy(loadState = LoadState.DATA, homeEntity = homeEntity)
        }
    }
}