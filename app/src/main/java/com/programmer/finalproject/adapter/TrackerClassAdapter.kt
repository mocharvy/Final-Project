package com.programmer.finalproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.programmer.finalproject.databinding.ItemCourseBinding
import com.programmer.finalproject.model.courses.Courses
import com.programmer.finalproject.model.courses.DataItem
import com.programmer.finalproject.model.courses.me.Data

class TrackerClassAdapter(
    private val context: Context,
    private val onItemClick: (String) -> Unit
) : ListAdapter<Data, TrackerClassAdapter.TrackerViewHolder>(Differ()) {

    var course = emptyList<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackerViewHolder {
        return TrackerViewHolder.from(parent, context)
    }

    override fun onBindViewHolder(holder: TrackerViewHolder, position: Int) {
        val currentCourse = course[position]
        holder.bindData(currentCourse, onItemClick)
    }

    class TrackerViewHolder private constructor(
        private val binding: ItemCourseBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

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

        companion object {
            fun from(parent: ViewGroup, context: Context): TrackerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCourseBinding.inflate(layoutInflater, parent, false)
                return TrackerViewHolder(binding, context)
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