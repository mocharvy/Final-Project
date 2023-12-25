package com.programmer.finalproject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.ItemChaptersBinding
import com.programmer.finalproject.model.chapter.ModulesItem
import com.programmer.finalproject.model.detailcourse.ChaptersItem

class ChapterAdapter(
    private val context: Context,
    private val chapters: List<ChaptersItem?>?,
    private val onChapterClick: (String) -> Unit
) : RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>() {

    private val lessonAdapters = mutableMapOf<String, LessonAdapter>()

    init {
        Log.d("ChapterAdapter", "Modules: $chapters")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val binding =
            ItemChaptersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        val chapter = chapters?.get(position)
        holder.bind(chapter)

        holder.itemView.setOnClickListener {
            val chapterId = chapters?.get(position)?.id
            if (chapterId != null) {
                onChapterClick(chapterId)
            }
        }

        val chapterId = chapter?.id
        if (chapterId != null) {
            val lessonAdapter = lessonAdapters.getOrPut(chapterId) {
                LessonAdapter(context, listOf())
            }
            holder.binding.rvLesson.adapter = lessonAdapter
            holder.binding.rvLesson.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

    }

    override fun getItemCount(): Int {
        return chapters?.size ?: 0
    }

    inner class ChapterViewHolder(val binding: ItemChaptersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val chapterIndex: TextView = itemView.findViewById(R.id.tv_chapter_number)
        private val chapterDuration: TextView = itemView.findViewById(R.id.tv_total_minute)
        private val chapterName: TextView = itemView.findViewById(R.id.tv_chapter_name)

        fun bind(chaptersItem: ChaptersItem?) {
            chapterIndex.text = chaptersItem?.index.toString()
            chapterDuration.text = chaptersItem?.moduleDuration.toString()
            chapterName.text = chaptersItem?.name

        }
    }

    fun updateModules(chapterId: String, modules: List<ModulesItem?>?) {
        lessonAdapters[chapterId]?.updateModules(modules)
    }
}