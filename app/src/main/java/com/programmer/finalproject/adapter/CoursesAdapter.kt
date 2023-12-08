package com.programmer.finalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.programmer.finalproject.databinding.ItemCourseBinding
import com.programmer.finalproject.model.courses.Courses

class CoursesAdapter : ListAdapter<Courses, CoursesAdapter.CoursesViewHolder>(Differ()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {
        val binding =
            ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoursesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }


    inner class CoursesViewHolder(
        private val binding: ItemCourseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(courses: Courses) {
            binding.apply {
                tvAuthor.text = courses.facilitator
                tvDesc.text = courses.category.category
                ivCourseImage.load(courses.category.image)
                tvLevel.text= courses.level
                tvModule.text = courses.total_chapter.toString()
                tvTime.text = courses.total_duration.toString()
                btPrice.text = "Beli Rp${courses.price}"
            }

            binding.root.setOnClickListener {
            }
        }
    }

    class Differ : DiffUtil.ItemCallback<Courses>() {
        override fun areItemsTheSame(oldItem: Courses, newItem: Courses): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Courses, newItem: Courses): Boolean {
            return oldItem == newItem
        }
    }


}