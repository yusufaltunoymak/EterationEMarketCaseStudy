package com.altunoymak.eterationemarketcasestudy.presentation.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.altunoymak.eterationemarketcasestudy.base.BaseFragment
import com.altunoymak.eterationemarketcasestudy.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private lateinit var profileAdapter: ProfileAdapter
    private val profileViewModel : ProfileViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeData()
    }

    private fun initAdapter() {
        profileAdapter = ProfileAdapter() { id ->
            lifecycleScope.launch {
                profileViewModel.deleteOrder(id)
            }
        }
        binding.orderRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.orderRv.adapter = profileAdapter
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            profileViewModel.viewState.collect { viewState ->
                viewState.orderList?.let { orderList ->
                    profileAdapter.submitList(orderList)
                    if(orderList.isEmpty()) {
                        binding.emptyListTv.visibility = View.VISIBLE
                        binding.orderRv.visibility = View.GONE
                    }
                    else {
                        binding.emptyListTv.visibility = View.GONE
                    }
                }
            }
        }
    }
}