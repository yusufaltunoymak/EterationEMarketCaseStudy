package com.altunoymak.eterationemarketcasestudy.presentation.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.altunoymak.eterationemarketcasestudy.data.local.model.Order
import com.altunoymak.eterationemarketcasestudy.databinding.ProfileRecyclerItemBinding
import com.altunoymak.eterationemarketcasestudy.util.Constants
import com.altunoymak.eterationemarketcasestudy.util.downloadFromUrl
import java.text.SimpleDateFormat
import java.util.Locale

class ProfileAdapter(private val onDeleteClick: (Int) -> Unit) : ListAdapter<Order, ProfileAdapter.ViewHolder>(ProfileDiffCallBack()) {

    inner class ViewHolder(private val binding: ProfileRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.apply {
                orderDateTv.text = formattedDate(order.orderDate)
                totalPriceTv.text = "${order.totalPrice} ₺"
                posterIv.downloadFromUrl(order.image, binding.root.context)
                deleteOrderButton.setOnClickListener { onDeleteClick(order.id!!) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ProfileRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order)
    }
}

private fun formattedDate(date: String?): String {
    val inputFormat = SimpleDateFormat(Constants.simpleDateFormat, Locale.getDefault())
    val outputFormat = SimpleDateFormat(Constants.dateFormat, Locale.getDefault())
    val dateTime = inputFormat.parse(date)
    return outputFormat.format(dateTime)
}

class ProfileDiffCallBack : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}