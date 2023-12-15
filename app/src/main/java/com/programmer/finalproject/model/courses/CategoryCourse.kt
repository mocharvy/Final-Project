package com.programmer.finalproject.model.courses


import com.google.gson.annotations.SerializedName

data class CategoryCourse(
    @SerializedName("detailCategory")
    val category: String,
    @SerializedName("image")
    val image: String
)