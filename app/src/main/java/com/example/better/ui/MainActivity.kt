package com.example.better.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.better.R
import com.example.better.contracts.MainContract.*
import com.example.better.core.BaseActivity
import com.example.better.core.ViewAction
import com.example.better.databinding.ActivityMainBinding
import com.example.better.repo.HomeRepository
import com.example.better.utils.BindActivity
import com.example.better.vms.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ViewState, ViewEvent>() {

    override val mBinding: ActivityMainBinding by BindActivity(R.layout.activity_main)

    // 这里不通过viewModelFactory创建了。通过viewModelFactory方式创建可以自己手动控制MainViewModel的创建方式，如构造注入字段。
    // 支持Hilt注入则没必要这样搞了。
    override val mViewModel by viewModels<MainViewModel>()


    override fun initView() {
        mBinding.run {
            bottomNavigationView.setupWithNavController(getNavController())
        }
    }

    override fun onDisplayScreenAction(it: ViewAction.DisplayScreen<*>) {

    }

    override fun onViewStateUpdate(viewState: ViewState) {
        Timber.d("MainActivity-onViewStateUpdate:${viewState}")
    }


    private fun getNavController() =
        (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).navController
}