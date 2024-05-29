package com.altunoymak.eterationemarketcasestudy.presentation.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.altunoymak.eterationemarketcasestudy.R
import com.altunoymak.eterationemarketcasestudy.base.BaseFragment
import com.altunoymak.eterationemarketcasestudy.data.local.model.Product
import com.altunoymak.eterationemarketcasestudy.databinding.FragmentBasketBinding
import com.altunoymak.eterationemarketcasestudy.util.clickWithDebounce
import com.altunoymak.eterationemarketcasestudy.util.mapToOrder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BasketFragment : BaseFragment<FragmentBasketBinding>(FragmentBasketBinding::inflate) {
    private lateinit var basketAdapter: BasketAdapter
    private val productDatabaseViewModel : ProductDatabaseViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            productDatabaseViewModel.viewState.collect() { viewState ->
                viewState.apply {
                    binding.apply {
                        productList?.let { productsList ->
                            basketAdapter.submitList(productsList)
                            calculateTotalPrice(productsList)
                            if(productsList.isEmpty()) {
                                basketRv.visibility = View.GONE
                                priceTv.visibility = View.GONE
                                staticTotalPriceTv.visibility = View.GONE
                                completeButton.visibility = View.GONE
                                emptyListTv.visibility = View.VISIBLE
                            }
                            else {
                                basketRv.visibility = View.VISIBLE
                                staticTotalPriceTv.visibility = View.VISIBLE
                                priceTv.visibility = View.VISIBLE
                                completeButton.visibility = View.VISIBLE
                                emptyListTv.visibility = View.GONE
                            }
                        }
                        completeButton.clickWithDebounce {
                            productList?.let { productsList ->
                                productDatabaseViewModel.getOrder(productsList[0].productId).observe(viewLifecycleOwner) { order ->
                                    if (order == null) {
                                        productDatabaseViewModel.insertOrder(mapToOrder(productsList))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun calculateTotalPrice(products: List<Product>) {
        val totalPrice = products.sumOf { it.price.toDouble() * it.quantity }
        binding.priceTv.text = "${totalPrice} ${getString(R.string.currency_symbol)}"
    }

    private fun initAdapter() {
        basketAdapter = BasketAdapter(
            onQuantityChange = { product, newQuantity ->
                productDatabaseViewModel.updateProductQuantity(product.id!!, newQuantity)
            },
            onProductDeleted = { product ->
                productDatabaseViewModel.deleteProduct(product.id!!)
            }
        )
        binding.basketRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = basketAdapter
            itemAnimator = null
        }
    }
}