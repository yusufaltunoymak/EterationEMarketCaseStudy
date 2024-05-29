package com.altunoymak.eterationemarketcasestudy.presentation.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.altunoymak.eterationemarketcasestudy.base.BaseFragment
import com.altunoymak.eterationemarketcasestudy.databinding.FragmentFavoriteBinding
import com.altunoymak.eterationemarketcasestudy.util.toProductResponseItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val favoriteViewModel : FavoriteViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            favoriteViewModel.viewState.collect { viewState ->
                favoriteAdapter.submitList(viewState.favList)
                if(viewState.favList?.isEmpty() == true) {
                    binding.emptyListTv.visibility = View.VISIBLE
                    binding.favoriteRv.visibility = View.GONE
                }
                else {
                    binding.emptyListTv.visibility = View.GONE
                    binding.favoriteRv.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initAdapter() {
        favoriteAdapter = FavoriteAdapter() { favoriteProduct ->
            val productResponseItem = favoriteProduct.toProductResponseItem()
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToProductDetailFragment(productResponseItem)
            findNavController().navigate(action)
        }
        binding.favoriteRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.favoriteRv.adapter = favoriteAdapter
    }
}