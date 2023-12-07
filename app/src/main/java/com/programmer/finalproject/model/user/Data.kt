package com.programmer.finalproject.model.user


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("city")
    val city: Any,
    @SerializedName("country")
    val country: Any,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("photo")
    val photo: Any,
    @SerializedName("role")
    val role: String,
    @SerializedName("tokenResetPassword")
    val tokenResetPassword: Any,
    @SerializedName("updatedAt")
    val updatedAt: String
)