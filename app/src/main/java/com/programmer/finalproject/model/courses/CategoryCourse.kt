package com.programmer.finalproject.model.courses


import com.google.gson.annotations.SerializedName

data class CategoryX(
    @SerializedName("category")
    val category: String,
    @SerializedName("image")
    val image: String
)