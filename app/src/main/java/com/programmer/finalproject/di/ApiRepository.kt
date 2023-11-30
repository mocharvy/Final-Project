package com.programmer.finalproject.di

import com.programmer.finalproject.model.register.RegisterRequest
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiHelper: ApiHelper) {
    fun register(registerRequest: RegisterRequest) = apiHelper.register(registerRequest)

    fun getCourses() = apiHelper.getCourses()

}