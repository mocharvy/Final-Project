package com.programmer.finalproject.di

import com.programmer.finalproject.model.courses.CoursesResponse
import com.programmer.finalproject.model.register.RegisterRequest
import com.programmer.finalproject.model.register.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    fun registerUser(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>


    @GET("courses")
    fun getCourses(): Call <CoursesResponse>

}