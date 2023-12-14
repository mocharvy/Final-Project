package com.programmer.finalproject.model.payment

data class Order(
    val course_id: String,
    val createdAt: String,
    val id: String,
    val order_method: Any,
    val payment_date: Any,
    val status: String,
    val updatedAt: String,
    val user_id: String
)