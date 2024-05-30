package com.altunoymak.eterationemarketcasestudy.presentation.ui.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.altunoymak.eterationemarketcasestudy.databinding.BrandRecyclerItemBinding

class BrandAdapter : ListAdapter<String, BrandAdapter.ViewHolder>(BrandDiffCallback()) {

    private val selectedBrands = mutableListOf<String>()
    fun getSelectedBrands(): List<String> {
        return selectedBrands
    }
    inner class ViewHolder(private val binding: BrandRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(brand: String) {
            binding.brandText.text = brand
            binding.brandCheckBox.isChecked = selectedBrands.contains(brand)

            val clickListener = View.OnClickListener {
                if (selectedBrands.contains(brand)) {
                    selectedBrands.remove(brand)
                } else {
                    selectedBrands.add(brand)
                }
                notifyItemChanged(adapterPosition)
            }

            binding.root.setOnClickListener(clickListener)
            binding.brandCheckBox.setOnClickListener(clickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BrandRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class BrandDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}