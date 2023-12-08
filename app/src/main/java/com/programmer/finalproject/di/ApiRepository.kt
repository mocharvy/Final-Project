package com.programmer.finalproject.di

import com.programmer.finalproject.model.login.LoginRequest
import com.programmer.finalproject.model.register.RegisterRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiHelper: ApiHelper) {
    fun register(registerRequest: RegisterRequest) = apiHelper.register(registerRequest)
    fun login(loginRequest: LoginRequest) = apiHelper.login(loginRequest)

    fun getCourses() = apiHelper.getCourses()
    fun getListCategory() = apiHelper.getCategories()

    suspend fun editProfile(name: RequestBody, email: RequestBody, phone_number: RequestBody, country: RequestBody, city: RequestBody, photo: MultipartBody.Part, token:String) =
        apiHelper.editProfile(name,email,phone_number,country,city,photo,token)
}