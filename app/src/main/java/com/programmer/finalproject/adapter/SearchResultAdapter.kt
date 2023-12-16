package com.programmer.finalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.programmer.finalproject.databinding.ItemCourseBinding
import com.programmer.finalproject.model.courses.Courses

class SearchResultAdapter(private var onCourseClickListener: (Courses) -> Unit) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

//    private var resultList = ArrayList<Meal>()

    private val mDiffUtil = object : DiffUtil.ItemCallback<Courses>() {
        override fun areItemsTheSame(oldItem: Courses, newItem: Courses): Boolean {
            return oldItem.id== newItem.id
        }

        override fun areContentsTheSame(oldItem: Courses, newItem: Courses): Boolean {
            return oldItem == newItem
        }
    }

    val mDiffer = AsyncListDiffer(this, mDiffUtil)

    inner class SearchResultViewHolder(
        var binding: ItemCourseBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            ItemCourseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        var courses = mDiffer.currentList[position]
        holder.binding.tvAuthor.text = courses.facilitator
        holder.binding.tvDesc.text = courses.category.category
        holder.binding.ivCourseImage.load(courses.category.image)
        holder.binding.tvLevel.text= courses.level
        holder.binding.tvModule.text = courses.total_chapter.toString()
        holder.binding.tvTime.text = courses.total_duration.toString()
        holder.binding.btPrice.text = "Beli Rp${courses.price}"

        holder.itemView.setOnClickListener {
            val course = mDiffer.currentList[position]
            onCourseClickListener.invoke(course)
        }
    }
    fun setOnCourseClickListener(listener: (Courses) -> Unit) {
        onCourseClickListener = listener
    }
    override fun getItemCount(): Int {

        return mDiffer.currentList.size
    }

}