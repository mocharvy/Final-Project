package com.programmer.finalproject.model.user


import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @SerializedName("dataDetailCourse2")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)