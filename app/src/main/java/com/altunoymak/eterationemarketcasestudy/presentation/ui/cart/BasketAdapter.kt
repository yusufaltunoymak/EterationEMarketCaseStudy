package com.altunoymak.eterationemarketcasestudy.presentation.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.altunoymak.eterationemarketcasestudy.data.local.model.Product
import com.altunoymak.eterationemarketcasestudy.databinding.CardRecyclerItemBinding

class BasketAdapter(
    private val onQuantityChange: (Product, Int) -> Unit,
    private val onProductDeleted: (Product) -> Unit
) : ListAdapter<Product,BasketAdapter.ViewHolder>(BasketDiffCallBack()) {
    inner class ViewHolder(private val binding: CardRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            binding.apply {
                cardProductNameTv.text = product.name
                cardProductPriceTv.text = "${product.price.toDouble() * product.quantity} ₺"
                quantityButton.text = product.quantity.toString()

                plusButton.setOnClickListener {
                    val newQuantity = product.quantity + 1
                    onQuantityChange(product.copy(quantity = newQuantity), newQuantity)
                }

                minusButton.setOnClickListener {
                    if (product.quantity > 1) {
                        val newQuantity = product.quantity - 1
                        onQuantityChange(product.copy(quantity = newQuantity), newQuantity)
                    }
                    else {
                        onProductDeleted(product)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}
class BasketDiffCallBack() : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}