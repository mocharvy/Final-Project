package com.programmer.finalproject.model.payment

data class HistoryPaymentResponse(
    val `data`: List<Data>,
    val message: String,
    val status: String
)