package com.programmer.finalproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.programmer.finalproject.databinding.ItemLessonBinding
import com.programmer.finalproject.model.chapter.ModulesItem

class LessonAdapter(
    private val context: Context,
    private var lessons: List<ModulesItem>?,
    private val onLessonClick: ((String) -> Unit?)? = null
) : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val binding = ItemLessonBinding.inflate(LayoutInflater.from(context), parent, false)
        return LessonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val lesson = lessons?.get(position)
        holder.binding.tvName.text = lesson?.name
        holder.binding.tvIndex.text = lesson?.index.toString()

        holder.itemView.setOnClickListener {
            val videoUrl = lesson?.video
            val moduleId = lesson?.id

            if (videoUrl != null && moduleId != null) {
                onLessonClick?.let { it1 ->
                    it1(videoUrl)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return lessons?.size ?: 0
    }

    class LessonViewHolder(val binding: ItemLessonBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateModules(modules: List<ModulesItem?>?) {
        lessons = modules as List<ModulesItem>?
        notifyDataSetChanged()
    }
}