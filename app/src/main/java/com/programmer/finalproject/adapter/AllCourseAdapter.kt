package com.programmer.finalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.programmer.finalproject.databinding.ItemCourseBinding
import com.programmer.finalproject.model.courses.AllCoursesResponse
import com.programmer.finalproject.model.courses.DataCourse
import com.programmer.finalproject.utils.CourseDiffUtil

class AllCourseAdapter(
    private val listener: OnItemClickListener? = null
) : RecyclerView.Adapter<AllCourseAdapter.MyViewHolder>() {

    private var course = emptyList<DataCourse>()

    class MyViewHolder(private val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataCourse: DataCourse) {
                binding.apply {
                    ivCourseImage.load(dataCourse.category.image)
                    tvTitle.text = dataCourse.name
                    tvDesc.text = dataCourse.category.category
                    tvAuthor.text = dataCourse.facilitator
                    tvTime.text = dataCourse.totalDuration.toString()
                    tvModule.text = dataCourse.totalChapter.toString()
                    tvLevel.text = dataCourse.level
                    btPrice.text = dataCourse.price.toString()
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
        holder.bind(currentCourse)
    }

    override fun getItemCount(): Int {
        return course.size
    }

    fun setData(newData: AllCoursesResponse) {
        val courseDiffUtil = CourseDiffUtil(course, newData.data)
        val diffUtilResult = DiffUtil.calculateDiff(courseDiffUtil)
        course = newData.data
        diffUtilResult.dispatchUpdatesTo(this)
    }

}