package com.altunoymak.eterationemarketcasestudy.presentation.ui.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.altunoymak.eterationemarketcasestudy.databinding.ModelRecyclerItemBinding

class ModelAdapter : ListAdapter<String, ModelAdapter.ViewHolder>(ModelDiffCallback()) {

    inner class ViewHolder(private val binding: ModelRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: String) {
            binding.modelText.text = model

            binding.root.setOnClickListener {
                binding.modelCheckBox.isChecked = !binding.modelCheckBox.isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ModelRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ModelDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}