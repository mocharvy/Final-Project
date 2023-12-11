package com.programmer.finalproject.model.user.password

data class ChangePasswordRequest(
    val confirm_password: String,
    val new_password: String,
    val old_password: String
)