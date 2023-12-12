package com.programmer.finalproject.model.login

data class LoginRequest(
    val emailOrPhone: String,
    val password: String
)