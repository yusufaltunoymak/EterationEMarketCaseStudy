package com.altunoymak.eterationemarketcasestudy.presentation.ui.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.altunoymak.eterationemarketcasestudy.databinding.ModelRecyclerItemBinding

class ModelAdapter : ListAdapter<String, ModelAdapter.ViewHolder>(ModelDiffCallback()) {
    private val selectedPositions = mutableSetOf<Int>()

    inner class ViewHolder(private val binding: ModelRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var itemPosition: Int = -1

        init {
            binding.root.setOnClickListener {
                if (itemPosition in selectedPositions) {
                    selectedPositions.remove(itemPosition)
                    binding.modelCheckBox.isChecked = false
                } else {
                    selectedPositions.add(adapterPosition)
                    binding.modelCheckBox.isChecked = true
                }
            }
        }

        fun bind(model: String, position: Int) {
            this.itemPosition = position
            binding.modelText.text = model
            binding.modelCheckBox.isChecked = position in selectedPositions
        }
    }

    fun getSelectedModels(): List<String> {
        return selectedPositions.map { position -> getItem(position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ModelRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
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