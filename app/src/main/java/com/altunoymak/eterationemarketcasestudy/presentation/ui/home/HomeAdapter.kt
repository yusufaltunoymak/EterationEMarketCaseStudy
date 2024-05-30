package com.altunoymak.eterationemarketcasestudy.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.altunoymak.eterationemarketcasestudy.data.remote.model.ProductResponseItem
import com.altunoymak.eterationemarketcasestudy.databinding.HomeRecyclerItemBinding
import com.altunoymak.eterationemarketcasestudy.util.convertResponseItemToEntity
import com.altunoymak.eterationemarketcasestudy.util.downloadFromUrl
import kotlinx.coroutines.launch

class HomeAdapter(
    private val productActions: ProductActions,
    private val lifecycleOwner: LifecycleOwner,
    val onItemClicked: (ProductResponseItem) -> Unit
) : ListAdapter<ProductResponseItem, HomeAdapter.ViewHolder>(HomeDiffCallBack()) {
    inner class ViewHolder(private val binding: HomeRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductResponseItem) {
            binding.apply {
                productNameTv.text = product.name
                productPriceTv.text = "${product.price.toString()} ₺"
                itemImage.downloadFromUrl(product.image, binding.root.context)
                root.setOnClickListener {
                    onItemClicked.invoke(product)
                }
                productActions.checkIfFavoriteProduct(product.id!!).observe(lifecycleOwner) {
                    favoriteIcon.isSelected = it
                }
                favoriteIcon.setOnClickListener {
                    if (favoriteIcon.isSelected) {
                        productActions.removeFavoriteProduct(product.id!!)
                    } else {
                        productActions.addProductToFavorites(product)
                    }
                    favoriteIcon.isSelected = !favoriteIcon.isSelected
                }
                buyButton.setOnClickListener {
                    lifecycleOwner.lifecycleScope.launch {
                        productActions.addToCart(convertResponseItemToEntity(product))
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HomeRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}

class HomeDiffCallBack : DiffUtil.ItemCallback<ProductResponseItem>() {
    override fun areItemsTheSame(
        oldItem: ProductResponseItem,
        newItem: ProductResponseItem
    ): Boolean {
        return oldItem == newItem

    }

    override fun areContentsTheSame(
        oldItem: ProductResponseItem,
        newItem: ProductResponseItem
    ): Boolean {
        return oldItem == newItem
    }

}