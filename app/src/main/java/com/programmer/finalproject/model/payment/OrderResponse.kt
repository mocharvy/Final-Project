package com.programmer.finalproject.model.payment

data class OrderResponse(
    val `data`: Order,
    val message: String,
    val status: String
)