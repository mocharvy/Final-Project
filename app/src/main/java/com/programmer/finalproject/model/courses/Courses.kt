package com.programmer.finalproject.model.courses

data class Courses(
    val category: Category,
    val facilitator: String,
    val id: String,
    val level: String,
    val name: String,
    val price: Int,
    val total_chapter: Int,
    val total_duration: Int,
    val type: String
)