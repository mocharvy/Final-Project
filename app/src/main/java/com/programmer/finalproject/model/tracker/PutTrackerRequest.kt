package com.programmer.finalproject.model.tracker

data class PutTrackerRequest(
    val last_opened_chapter: Int,
    val last_opened_module: Int,
    val module_id: String
)
