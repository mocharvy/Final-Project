package com.programmer.finalproject.model.courses


import com.google.gson.annotations.SerializedName

data class AllCoursesResponse(
    @SerializedName("dataDetailCourse2")
    val `data`: List<DataCourse>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)