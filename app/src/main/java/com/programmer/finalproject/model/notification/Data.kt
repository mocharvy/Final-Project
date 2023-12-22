package com.programmer.finalproject.model.notification

data class Data(
    val additional_message: Any,
    val date_notif: String,
    val id: String,
    val is_readed: Boolean,
    val message: String,
    val title: String
)