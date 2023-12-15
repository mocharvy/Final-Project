package com.programmer.finalproject.model.courses

import com.google.gson.annotations.SerializedName

data class DataCourse(
    @SerializedName("detailCategory")
    val category: CategoryCourse,
    @SerializedName("facilitator")
    val facilitator: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("level")
    val level: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("total_chapter")
    val totalChapter: Int,
    @SerializedName("total_duration")
    val totalDuration: Int,
    @SerializedName("type")
    val type: String
)