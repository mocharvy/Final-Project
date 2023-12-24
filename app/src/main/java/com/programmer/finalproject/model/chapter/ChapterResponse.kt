package com.programmer.finalproject.model.chapter

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ChapterResponse(

	@field:SerializedName("data")
	val data: DataChapter? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class Course(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable

@Parcelize
data class DataChapter(

	@field:SerializedName("is_locked")
	val isLocked: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("index")
	val index: Int? = null,

	@field:SerializedName("total_module_duration")
	val totalModuleDuration: Int? = null,

	@field:SerializedName("course")
	val course: Course? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("modules")
	val modules: List<ModulesItem?>? = null
) : Parcelable

@Parcelize
data class ModulesItem(

	@field:SerializedName("duration")
	val duration: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("index")
	val index: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("video")
	val video: String? = null
) : Parcelable
