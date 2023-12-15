package com.programmer.finalproject.di

import com.programmer.finalproject.model.courses.CategoryResponse
import com.programmer.finalproject.model.courses.AllCoursesResponse
import com.programmer.finalproject.model.courses.CoursesResponse
import com.programmer.finalproject.model.login.LoginRequest
import com.programmer.finalproject.model.login.LoginResponse
import com.programmer.finalproject.model.register.RegisterRequest
import com.programmer.finalproject.model.register.RegisterResponse
<<<<<<< Updated upstream
import com.programmer.finalproject.model.user.UserDetailResponse
import com.programmer.finalproject.model.user.password.ChangePasswordRequest
import com.programmer.finalproject.model.user.password.ChangePasswordResponse
import com.programmer.finalproject.model.user.password.ResetPasswordResponse
import com.programmer.finalproject.model.user.password.ResetPasswordRequest
import com.programmer.finalproject.model.user.update.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
=======
>>>>>>> Stashed changes
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

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
    suspend fun getAllCourses(): Response<AllCoursesResponse>


<<<<<<< Updated upstream
    @GET("orders")
      fun getHistoryPayment(
        @Header("Authorization") token: String,
        ): Call<OrdersResponse>
    @GET("courses")
    fun getCourseByName(
        @Query("name") name:String
    ): Call <CoursesResponse>

    @POST("reset/password")
    fun resetPassword(
        @Body resetPasswordResponse: ResetPasswordRequest,
    ): Call<ResetPasswordResponse>
=======
>>>>>>> Stashed changes
}