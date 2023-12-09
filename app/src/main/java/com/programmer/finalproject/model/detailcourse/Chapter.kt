package com.programmer.finalproject.model.detailcourse


import com.google.gson.annotations.SerializedName

data class Chapter(
    @SerializedName("id")
    val id: String,
    @SerializedName("index")
    val index: Int,
    @SerializedName("is_locked")
    val isLocked: Boolean,
    @SerializedName("name")
    val name: String
)