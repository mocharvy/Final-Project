package com.programmer.finalproject.model.notification

data class NotificationResponse(
    val `data`: List<Data>,
    val message: String,
    val status: String
)