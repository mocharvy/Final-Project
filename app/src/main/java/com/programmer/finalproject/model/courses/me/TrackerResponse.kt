package com.programmer.finalproject.model.courses.me

data class TrackerResponse(
    val `data`: List<Data>,
    val message: String,
    val status: String
)