package com.programmer.finalproject.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.ItemCourseBinding
import com.programmer.finalproject.databinding.PlaceholderNotificationBinding
import com.programmer.finalproject.model.courses.Courses
import com.programmer.finalproject.model.notification.Data
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class NotificationAdapter (private val onNotificationClick: (Data) -> Unit): ListAdapter<Data, NotificationAdapter.NotificationViewHolder>(Differ()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding =
            PlaceholderNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

     override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bindData(getItem(position))
         holder.itemView.setOnClickListener {
             val notif = getItem(position)
             onNotificationClick.invoke(notif)
         }
    }


    inner class NotificationViewHolder(
        private val binding: PlaceholderNotificationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

         fun bindData(notif: Data) {
            binding.apply {
                titleNotification.text = notif.title
                textNotification.text = notif.message
                val inputDate = notif.createdAt
                val formattedDate = formatDateString(inputDate)
                textDate.text = formattedDate

                if(notif.is_readed){
                    ivIndicatorNotification.setImageResource(R.drawable.ellipse_1)
                }else{
                    ivIndicatorNotification.setImageResource(R.drawable.ellipse_2)

                }



            }

            binding.root.setOnClickListener {
            }
        }
    }

    fun formatDateString(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID")) // Locale for Bahasa Indonesia

        try {
            val date = inputFormat.parse(inputDate)
            return outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
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