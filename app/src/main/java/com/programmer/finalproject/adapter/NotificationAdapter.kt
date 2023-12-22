package com.programmer.finalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.programmer.finalproject.databinding.ItemCourseBinding
import com.programmer.finalproject.databinding.PlaceholderNotificationBinding
import com.programmer.finalproject.model.notification.Data

class NotificationAdapter : ListAdapter<Data, NotificationAdapter.NotificationViewHolder>(Differ()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding =
            PlaceholderNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }


    inner class NotificationViewHolder(
        private val binding: PlaceholderNotificationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(notif: Data) {
            binding.apply {
                titleNotification.text = notif.title
                subTextNotification.text = notif.message

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