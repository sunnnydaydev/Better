package com.example.better.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
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
import com.example.better.utils.BindActivity
import com.example.better.vms.MainViewModel
import timber.log.Timber

class MainActivity : BaseActivity<ViewState, ViewEvent>() {

    override val mBinding: ActivityMainBinding by BindActivity(R.layout.activity_main)

    // 这里不通过viewModelFactory创建了。通过viewModelFactory方式创建可以自己手动控制MainViewModel的创建方式，如构造注入字段。
    // 支持Hilt注入则没必要这样搞了。
    override val mViewModel by viewModels<MainViewModel>()


    override fun initView() {
        initBottomNavigationView()
    }

    override fun onDisplayScreenAction(it: ViewAction.DisplayScreen<*>) {

    }

    override fun onViewStateUpdate(viewState: ViewState) {
        Timber.d("MainActivity-onViewStateUpdate:${viewState}")
    }

    /**
     * 1、NavigationUI.setupWithNavController(bottomNavigationView, navController);
     * 当BottomNavigationView的每个item对应独立一份导航图时这个方法就不起作用了。此时需要用如下方案实现。
     * 2、点击对应的item navigation导航到对应的fragment中，此时则面临另外一个问题，导航图中fragment注册问题如：
     * nav_home 想要跳转到 fragment_video,此时fragment_video也要注册到nav_home.xml 中
     * */
    private fun initBottomNavigationView() {
        mBinding.run {
           // bottomNavigationView.setupWithNavController(getNavController())
            NavigationUI.setupWithNavController(bottomNavigationView, getNavController())
        }
    }

    private fun getNavController() =
        (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).navController
}