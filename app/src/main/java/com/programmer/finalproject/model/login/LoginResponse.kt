package com.programmer.finalproject.model.login

data class LoginResponse(
    val `data`: Auth,
    val message: String,
    val status: String
)