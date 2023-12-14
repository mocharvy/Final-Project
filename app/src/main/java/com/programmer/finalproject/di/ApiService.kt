package com.programmer.finalproject.di

import com.programmer.finalproject.model.courses.CategoryResponse
import com.programmer.finalproject.model.courses.AllCoursesResponse2
import com.programmer.finalproject.model.courses.CoursesResponse
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse
import com.programmer.finalproject.model.login.LoginRequest
import com.programmer.finalproject.model.login.LoginResponse
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
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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

    @Multipart
    @PUT("user")
    suspend fun editProfile(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part("country") country: RequestBody,
        @Part("city") city: RequestBody,
        @Part photo: MultipartBody.Part,
        @Header("Authorization") authorization: String
    ): ProfileResponse

    @PUT("user/password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body changePasswordRequest: ChangePasswordRequest

    ):Response<ChangePasswordResponse>


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
}