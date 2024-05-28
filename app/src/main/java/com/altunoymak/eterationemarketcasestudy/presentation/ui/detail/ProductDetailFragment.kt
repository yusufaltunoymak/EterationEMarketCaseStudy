package com.altunoymak.eterationemarketcasestudy.presentation.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.altunoymak.eterationemarketcasestudy.R
import com.altunoymak.eterationemarketcasestudy.base.BaseFragment
import com.altunoymak.eterationemarketcasestudy.databinding.FragmentProductDetailBinding
import com.altunoymak.eterationemarketcasestudy.util.downloadFromUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>(FragmentProductDetailBinding::inflate) {
    private val args : ProductDetailFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val product = args.products
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if(!product.image.isNullOrEmpty()){
                productIv.downloadFromUrl(product.image, requireContext())
            }
            detailProductNameTv.text = product.name
            priceTv.text = product.price.toString() + getString(R.string.currency_symbol)
            productDetailTv.text = product.description
            product.name?.let {name ->
                detailToolbar.setToolbarText(text = name)
            }
        }
    }

}