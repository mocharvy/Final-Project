package com.programmer.finalproject.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.programmer.finalproject.databinding.ItemCourseBinding
import com.programmer.finalproject.model.courses.me.Data

class TrackerClassAdapter(
    private val onItemClick: (String) -> Unit
) : ListAdapter<Data, TrackerClassAdapter.TrackerViewHolder>(Differ()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackerViewHolder {
        val binding =
            ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackerViewHolder, position: Int) {
        holder.bindData(getItem(position), onItemClick)
    }


    inner class TrackerViewHolder(
        private val binding: ItemCourseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindData(tracker: Data, onItemClick: (String) -> Unit) {
            binding.apply {
                tvAuthor.text = tracker.course.facilitator
                tvDesc.text = tracker.course.name
                ivCourseImage.load(tracker.course.category.image)
                tvLevel.text = tracker.course.level
                tvModule.text = tracker.course.total_chapter.toString()
                tvTime.text = tracker.course.total_duration.toString()
                btPrice.text = tracker.progress_course.toString() + "%"
            }

            binding.root.setOnClickListener {
                onItemClick(tracker.course.id)
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