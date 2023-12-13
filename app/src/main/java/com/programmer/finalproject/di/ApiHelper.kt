package com.programmer.finalproject.di

import com.programmer.finalproject.model.login.LoginRequest
import com.programmer.finalproject.model.register.RegisterRequest
import com.programmer.finalproject.model.user.password.ResetPasswordRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import javax.inject.Inject

class ApiHelper @Inject constructor(private val api: ApiService) {
    fun register(registerRequest: RegisterRequest) = api.registerUser(registerRequest)
    fun login(loginRequest: LoginRequest) = api.loginUser(loginRequest)
    fun getCourses() = api.getCourses()
    fun getCategories() = api.getCategories()

    suspend fun editProfile(name:RequestBody,email:RequestBody,phone_number:RequestBody,country:RequestBody,city:RequestBody,photo:MultipartBody.Part,token:String) =
        api.editProfile(name,email,phone_number,country,city,photo,token)

    fun getHistoryPayment(token: String) = api.getHistoryPayment(token)
    fun getCoursesByName(name:String) = api.getCourseByName(name)

    fun resetPassword(resetPasswordRequest: ResetPasswordRequest)= api.resetPassword(resetPasswordRequest)

}