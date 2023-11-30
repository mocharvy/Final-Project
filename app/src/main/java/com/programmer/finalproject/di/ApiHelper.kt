package com.programmer.finalproject.di

import com.programmer.finalproject.model.register.RegisterRequest
import javax.inject.Inject

class ApiHelper @Inject constructor(private val api: ApiService) {
    fun register(registerRequest: RegisterRequest) = api.registerUser(registerRequest)
    fun getCourses() = api.getCourses()

}