package com.programmer.finalproject.model.register

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val no_telp: String,
)