package com.example.better.ui.fragments

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.better.R
import com.example.better.contracts.HomeFragmentContract.*
import com.example.better.core.BaseFragment
import com.example.better.core.BaseViewModel
import com.example.better.core.ViewAction
import com.example.better.databinding.FragmentHomeBinding
import com.example.better.repo.HomeRepository
import com.example.better.utils.BindFragment
import com.example.better.vms.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment<ViewState, ViewEvent>() {


    override val binding: FragmentHomeBinding by BindFragment(R.layout.fragment_home)

    override val viewModel: BaseViewModel<ViewState, ViewEvent> by viewModels<HomeFragmentViewModel>()
    override fun initView() {
        binding.run {
            toolbarContainer.toolbar.setNavigationOnClickListener {
                //findNavController().navigateUp() 栈顶就不能继续pop了
                requireActivity().finish()
            }
        }

    }

    override fun onDisplayScreenAction(it: ViewAction.DisplayScreen<*>) {

    }

    override fun onViewStateUpdate(viewState: ViewState) {

    }

}