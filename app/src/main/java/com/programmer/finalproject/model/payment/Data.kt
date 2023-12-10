package com.programmer.finalproject.model.payment

data class Data(
    val course: Course,
    val id: String,
    val order_method: Any,
    val payment_date: Any,
    val status: String,
    val user: User
)