package com.example.better.vms

import androidx.lifecycle.viewModelScope
import com.example.better.contracts.HomeFragmentContract
import com.example.better.repo.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Create by SunnyDay /01/22 21:40:59
 */

@HiltViewModel
class HomeFragmentViewModel @Inject constructor() : HomeFragmentContract.ViewModel() {

    @Inject
    lateinit var repository: HomeRepository

    init {
        // 留个bug：这样会crash
       // Timber.d("HomeFragmentViewModel->${repository.localDataSource.getLocalData()}")
        viewModelScope.launch {
            Timber.d("HomeFragmentViewModel->${repository.localDataSource.getLocalData()}")
        }
    }

    override fun onViewEvent(event: HomeFragmentContract.ViewEvent) {

    }
}