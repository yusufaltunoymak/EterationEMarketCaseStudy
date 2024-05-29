package com.altunoymak.eterationemarketcasestudy.presentation.ui.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.altunoymak.eterationemarketcasestudy.databinding.BrandRecyclerItemBinding

class BrandAdapter : ListAdapter<String, BrandAdapter.ViewHolder>(BrandDiffCallback()) {

    inner class ViewHolder(private val binding: BrandRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(brand: String) {
            binding.brandText.text = brand

            binding.root.setOnClickListener {
                binding.brandCheckBox.isChecked = !binding.brandCheckBox.isChecked
            }
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