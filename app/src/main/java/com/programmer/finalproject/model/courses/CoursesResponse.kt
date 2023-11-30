package com.programmer.finalproject.model.courses

data class CoursesResponse(
    val `data`: List<Courses>,
    val message: String,
    val status: String
)