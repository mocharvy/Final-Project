package com.programmer.finalproject.model.courses

import com.google.gson.annotations.SerializedName

data class AllCoursesResponse2(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class CourseCategory(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)

data class DataItem(

	@field:SerializedName("total_duration")
	val totalDuration: Int? = null,

	@field:SerializedName("level")
	val level: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("total_chapter")
	val totalChapter: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("category")
	val category: CourseCategory? = null,

	@field:SerializedName("facilitator")
	val facilitator: String? = null
)
