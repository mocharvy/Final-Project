package com.programmer.finalproject.di

import com.programmer.finalproject.model.courses.CategoryResponse
import com.programmer.finalproject.model.courses.AllCoursesResponse2
import com.programmer.finalproject.model.courses.CoursesResponse
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse
import com.programmer.finalproject.model.login.LoginRequest
import com.programmer.finalproject.model.login.LoginResponse
import com.programmer.finalproject.model.register.RegisterRequest
import com.programmer.finalproject.model.register.RegisterResponse
import com.programmer.finalproject.model.user.UserDetailResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("register")
    fun registerUser(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @POST("login")
    fun loginUser(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>


    @GET("courses")
    fun getCourses(): Call <CoursesResponse>

    @GET("categories")
    fun getCategories() : Call <CategoryResponse>

    @GET("courses")
    //suspend fun getAllCourses(): Response<AllCoursesResponse>
    suspend fun getAllCourses(): Response<AllCoursesResponse2>

    @GET("courses/{courseId}")
    suspend fun getCourseById(
        @Path("courseId") courseId: String
    ): Response<DetailCourseResponse>

    @GET("user")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
    ): Response<UserDetailResponse>

}