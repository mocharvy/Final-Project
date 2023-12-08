package com.programmer.finalproject.model.payment

data class OrdersResponse(
    val `data`: List<Data>,
    val message: String,
    val status: String
)