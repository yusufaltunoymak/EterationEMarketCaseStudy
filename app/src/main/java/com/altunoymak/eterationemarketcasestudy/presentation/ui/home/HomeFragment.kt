package com.altunoymak.eterationemarketcasestudy.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altunoymak.eterationemarketcasestudy.base.BaseFragment
import com.altunoymak.eterationemarketcasestudy.data.remote.model.ProductResponseItem
import com.altunoymak.eterationemarketcasestudy.databinding.FragmentHomeBinding
import com.altunoymak.eterationemarketcasestudy.util.clickWithDebounce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),ProductActions {
    private val productViewModel : ProductViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeData()
        setupScrollListener()
        setupSearchBar()
        observeSearchView()
    }
    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            productViewModel.viewState.collect { viewState ->
                viewState.apply {
                    binding.apply {
                        products?.let { productsList ->
                            homeAdapter.submitList(productsList)
                        }
                        isLoading?.let {
                            if (it) {
                                homeProgressBar.visibility = View.VISIBLE
                            } else {
                                homeProgressBar.visibility = View.GONE
                            }
                            errorMessage?.let {errorMessage ->
                                retryButton.clickWithDebounce {
                                    productViewModel.getAllProducts()
                                    errorText.visibility = View.GONE
                                    retryButton.visibility = View.GONE
                                }
                                if (errorMessage.isNotBlank()) {
                                    errorText.visibility = View.VISIBLE
                                    retryButton.visibility = View.VISIBLE
                                    homeProgressBar.visibility = View.GONE
                                    errorText.text = errorMessage
                                } else {
                                    errorText.visibility = View.GONE
                                }
                            }
                        }
                    }
                }

            }
        }
    }
    private fun observeSearchView() {
        viewLifecycleOwner.lifecycleScope.launch {
            productViewModel.searchText.collect { searchText ->
                val filteredProducts = productViewModel.getFilteredProducts()
                homeAdapter.submitList(filteredProducts)
            }
        }
    }
    private fun setupSearchBar() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    productViewModel.setSearchText(it)
                }
                return true
            }
        })
    }
    private fun initAdapter() {
        homeAdapter = HomeAdapter(this, viewLifecycleOwner) { product ->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product))
        }
        binding.homeRv.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.homeRv.adapter = homeAdapter
    }
    private fun setupScrollListener() {
        binding.homeRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!productViewModel.viewState.value.isLoading!! && totalItemCount <= (lastVisibleItem + productViewModel.getItemsPerPage())) {
                    productViewModel.loadMoreItems()
                }
            }
        })
    }
    override fun addProductToFavorites(product: ProductResponseItem) {
        viewLifecycleOwner.lifecycleScope.launch {
            productViewModel.addProductToFavorites(product)
        }
    }
    override fun removeFavoriteProduct(productId: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            productViewModel.removeFavoriteProduct(productId)
        }
    }
    override fun checkIfFavoriteProduct(productId: String): LiveData<Boolean> {
        return productViewModel.checkIfFavoriteProduct(productId)
    }
}