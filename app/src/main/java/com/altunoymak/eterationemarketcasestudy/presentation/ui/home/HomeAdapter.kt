package com.altunoymak.eterationemarketcasestudy.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.altunoymak.eterationemarketcasestudy.data.remote.model.ProductResponseItem
import com.altunoymak.eterationemarketcasestudy.databinding.HomeRecyclerItemBinding
import com.altunoymak.eterationemarketcasestudy.util.downloadFromUrl

class HomeAdapter() : ListAdapter<ProductResponseItem, HomeAdapter.ViewHolder>(HomeDiffCallBack()) {
    inner class ViewHolder(private val binding : HomeRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductResponseItem) {
            binding.apply {
                productNameTv.text = product.name
                productPriceTv.text = "${product.price.toString()} ₺"
                itemImage.downloadFromUrl(product.image, binding.root.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}
class HomeDiffCallBack : DiffUtil.ItemCallback<ProductResponseItem>() {
    override fun areItemsTheSame(oldItem: ProductResponseItem, newItem: ProductResponseItem): Boolean {
        return oldItem == newItem

    }

    override fun areContentsTheSame(oldItem: ProductResponseItem, newItem: ProductResponseItem): Boolean {
        return oldItem == newItem
    }

}