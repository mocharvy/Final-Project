package com.programmer.finalproject.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.programmer.finalproject.databinding.ItemCourseBinding
import com.programmer.finalproject.model.payment.Data

class HistoryPaymentAdapter : ListAdapter<Data, HistoryPaymentAdapter.HistoryPaymentViewHolder>(Differ()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryPaymentViewHolder {
        val binding =
            ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryPaymentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryPaymentViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }


    inner class HistoryPaymentViewHolder(
        private val binding: ItemCourseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(payment: Data) {
            binding.apply {
                tvAuthor.text = payment.course.name
                tvDesc.text = payment.course.category.category

                if(payment.status == "BELUM BAYAR"){
                    btPrice.setBackgroundColor(Color.parseColor("#EF4444"))
                    btPrice.text = "Waiting For Payment"

                }
            }

            binding.root.setOnClickListener {
            }
        }
    }

    class Differ : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }


}