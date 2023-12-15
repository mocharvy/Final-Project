package com.programmer.finalproject.model.otp

data class OTPRequest(
    val code1: String,
    val code2: String,
    val code3: String,
    val code4: String,
    val code5: String,
    val code6: String
)