package com.programmer.finalproject.model.courses

data class CategoryResponse(
    val code: Int,
    val `data`: List<Category>,
    val message: String,
    val status: Boolean
)