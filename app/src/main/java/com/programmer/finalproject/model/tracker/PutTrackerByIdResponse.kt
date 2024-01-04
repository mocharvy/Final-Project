package com.programmer.finalproject.model.tracker

import com.google.gson.annotations.SerializedName

data class PutTrackerByIdResponse(

	@field:SerializedName("data")
	val data: DataTrackPut,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataTrackPut(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("last_opened_module")
	val lastOpenedModule: Int,

	@field:SerializedName("last_opened_chapter")
	val lastOpenedChapter: Int,

	@field:SerializedName("total_modules_viewed")
	val totalModulesViewed: Int,

	@field:SerializedName("progress_course")
	val progressCourse: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
