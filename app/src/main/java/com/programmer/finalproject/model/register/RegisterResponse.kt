package com.programmer.finalproject.model.register

data class RegisterResponse(
    val `data`: Register,
    val message: String,
    val status: String
)