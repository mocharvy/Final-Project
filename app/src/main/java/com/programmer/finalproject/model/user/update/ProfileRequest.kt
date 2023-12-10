package com.programmer.finalproject.model.user.update

data class ProfileRequest(

    val name: String,
    val email: String,
    val phone_number: String,
    val country: String,
    val city: String,
    val photo: String
)