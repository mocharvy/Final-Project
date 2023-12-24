package com.programmer.finalproject.model.payment.order

data class PutOrderRequest(
    val course_id: String,
    val order_method: String
)