package com.programmer.finalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.programmer.finalproject.databinding.ItemCourseBinding
import com.programmer.finalproject.model.courses.AllCoursesResponse2
import com.programmer.finalproject.model.courses.DataItem
import com.programmer.finalproject.utils.CourseDiffUtil

class AllCourseAdapter(
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<AllCourseAdapter.MyViewHolder>() {

    private var course = emptyList<DataItem>()

    class MyViewHolder(private val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataCourse: DataItem, onItemClick: (String) -> Unit) {
                binding.apply {
                    ivCourseImage.load(dataCourse.category?.image)
                    tvTitle.text = dataCourse.name
                    tvDesc.text = dataCourse.category?.category
                    tvAuthor.text = dataCourse.facilitator
                    tvTime.text = dataCourse.totalDuration.toString()
                    tvModule.text = dataCourse.totalChapter.toString()
                    tvLevel.text = dataCourse.level
                    btPrice.text = dataCourse.price.toString()

                    itemView.setOnClickListener {
                        dataCourse.id?.let { courseId ->
                            onItemClick(courseId)
                        }
                    }
                }
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCourseBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentCourse = course[position]
        holder.bind(currentCourse, onItemClick)
    }

    override fun getItemCount(): Int {
        return course.size
    }

    @Suppress("UNCHECKED_CAST")
    fun setData(newData: AllCoursesResponse2) {
        val newDataList = newData.data ?: emptyList<DataItem>()
        val courseDiffUtil = CourseDiffUtil(course, newDataList)
        val diffUtilResult = DiffUtil.calculateDiff(courseDiffUtil)
        course = newDataList as List<DataItem>
        diffUtilResult.dispatchUpdatesTo(this)
    }

}