package com.altunoymak.eterationemarketcasestudy.presentation.ui.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.altunoymak.eterationemarketcasestudy.databinding.SortByRecyclerItemBinding
import com.altunoymak.eterationemarketcasestudy.util.SortBy

class SortByAdapter : ListAdapter<SortBy, SortByAdapter.ViewHolder>(SortByDiffCallback()) {

    var selectedPosition = -1
    private var selectedSortBy: SortBy? = null

    fun getSelectedSortBy(): SortBy? {
        return selectedSortBy
    }
    inner class ViewHolder(private val binding: SortByRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sortBy: SortBy) {
            binding.sortByText.text = sortBy.name
            binding.sortByRadioButton.isChecked = adapterPosition == selectedPosition
            binding.sortByRadioButton.isClickable = false
            binding.root.setOnClickListener {
                selectedPosition = adapterPosition
                selectedSortBy = sortBy
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SortByRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SortByDiffCallback : DiffUtil.ItemCallback<SortBy>() {
        override fun areItemsTheSame(oldItem: SortBy, newItem: SortBy): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SortBy, newItem: SortBy): Boolean {
            return oldItem == newItem
        }
    }
}