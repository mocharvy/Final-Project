package com.programmer.finalproject.model.tracker

import com.google.gson.annotations.SerializedName

data class GetTrackerByIdResponse(

	@field:SerializedName("data")
	val data: DataTrack,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataTrack(

	@field:SerializedName("last_opened_module")
	val lastOpenedModule: Int,

	@field:SerializedName("last_opened_chapter")
	val lastOpenedChapter: Int,

	@field:SerializedName("total_modules_viewed")
	val totalModulesViewed: Int,

	@field:SerializedName("progress_course")
	val progressCourse: Int,

	@field:SerializedName("id")
	val id: String
)
