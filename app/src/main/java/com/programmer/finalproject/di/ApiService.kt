package com.programmer.finalproject.di

import com.programmer.finalproject.model.courses.CategoryResponse
import com.programmer.finalproject.model.courses.AllCoursesResponse
import com.programmer.finalproject.model.courses.CoursesResponse
import com.programmer.finalproject.model.login.LoginRequest
import com.programmer.finalproject.model.login.LoginResponse
 
import com.programmer.finalproject.model.otp.OTPRequest
import com.programmer.finalproject.model.otp.OTPResponse
import com.programmer.finalproject.model.payment.HistoryPaymentResponse
import com.programmer.finalproject.model.payment.OrderRequest
import com.programmer.finalproject.model.payment.OrderResponse
 
import com.programmer.finalproject.model.register.RegisterRequest
import com.programmer.finalproject.model.register.RegisterResponse
  
import com.programmer.finalproject.model.user.UserDetailResponse
import com.programmer.finalproject.model.user.password.ChangePasswordRequest
import com.programmer.finalproject.model.user.password.ChangePasswordResponse
import com.programmer.finalproject.model.user.password.ResetPasswordResponse
import com.programmer.finalproject.model.user.password.ResetPasswordRequest
import com.programmer.finalproject.model.user.update.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
 
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


     @GET("orders")
      fun getHistoryPayment(
        @Header("Authorization") token: String,
        ): Call<HistoryPaymentResponse>
    @GET("courses")
    fun getCourseByName(
        @Query("name") name:String
    ): Call <CoursesResponse>

    @POST("reset/password")
    fun resetPassword(
        @Body resetPasswordResponse: ResetPasswordRequest,
    ): Call<ResetPasswordResponse>
 

    @POST("orders")
    fun orderCourses(
        @Header ("Authorization") token:String,
        @Body orderRequest: OrderRequest
    ):Call<OrderResponse>

    @GET("otp")
    fun getOTP(
        @Header ("Authorization") accessToken: String,
        ):Call<OTPResponse>
    @POST("otp")
    fun postOTP(
        @Header ("Authorization") accessToken: String,
        @Body otpRequest: OTPRequest
    ): Call<OTPResponse>


 
}