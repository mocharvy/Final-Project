package com.programmer.finalproject.model.detailcourse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class DetailCourseResponse3(

	@field:SerializedName("data")
	val data: @RawValue Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
): Parcelable

data class Category(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)

data class ChaptersItem(

	@field:SerializedName("is_locked")
	val isLocked: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("index")
	val index: Int? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class Data(

	@field:SerializedName("on_boarding")
	val onBoarding: String? = null,

	@field:SerializedName("level")
	val level: String? = null,

	@field:SerializedName("chapters")
	val chapters: List<ChaptersItem?>? = null,

	@field:SerializedName("introduction_video")
	val introductionVideo: String? = null,

	@field:SerializedName("total_chapter")
	val totalChapter: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("facilitator")
	val facilitator: String? = null,

	@field:SerializedName("total_duration")
	val totalDuration: Int? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("telegram_group")
	val telegramGroup: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("category")
	val category: Category? = null
)
