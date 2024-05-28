package com.altunoymak.eterationemarketcasestudy.presentation.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.altunoymak.eterationemarketcasestudy.data.local.model.FavoriteProduct
import com.altunoymak.eterationemarketcasestudy.databinding.FavoriteRecyclerItemBinding
import com.altunoymak.eterationemarketcasestudy.util.downloadFromUrl

class FavoriteAdapter() : ListAdapter<FavoriteProduct, FavoriteAdapter.ViewHolder>(FavoriteDiffCallBack()) {
    inner class ViewHolder(private val binding: FavoriteRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: FavoriteProduct) {
            binding.apply {
                favoriteProductTv.text = product.name
                favoriteProductDetailTv.text = product.description
                favoriteIv.downloadFromUrl(product.image, binding.root.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FavoriteRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}

class FavoriteDiffCallBack : DiffUtil.ItemCallback<FavoriteProduct>() {
    override fun areItemsTheSame(
        oldItem: FavoriteProduct,
        newItem: FavoriteProduct
    ): Boolean {
        return oldItem == newItem

    }

    override fun areContentsTheSame(
        oldItem: FavoriteProduct,
        newItem: FavoriteProduct
    ): Boolean {
        return oldItem == newItem
    }

}