package com.programmer.finalproject.model.detailcourse


import com.google.gson.annotations.SerializedName

data class DataDetailCourse(
    @SerializedName("category")
    val category: CategoryLawas,
    @SerializedName("chapters")
    val chapters: List<Chapter>,
    @SerializedName("description")
    val description: String,
    @SerializedName("facilitator")
    val facilitator: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("introduction_video")
    val introductionVideo: String,
    @SerializedName("level")
    val level: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("on_boarding")
    val onBoarding: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("telegram_group")
    val telegramGroup: String,
    @SerializedName("type")
    val type: String
)