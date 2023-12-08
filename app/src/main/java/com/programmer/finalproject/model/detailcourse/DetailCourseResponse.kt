package com.programmer.finalproject.model.detailcourse


import com.google.gson.annotations.SerializedName

data class DetailCourseResponse(
    @SerializedName("dataDetailCourse")
    val dataDetailCourse: DataDetailCourse,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)