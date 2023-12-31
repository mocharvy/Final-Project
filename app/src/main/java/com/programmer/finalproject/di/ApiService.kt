package com.programmer.finalproject.di


import com.programmer.finalproject.model.chapter.ChapterResponse
import com.programmer.finalproject.model.courses.AllCoursesResponse2
import com.programmer.finalproject.model.courses.CategoryResponse
import com.programmer.finalproject.model.courses.CoursesResponse
import com.programmer.finalproject.model.courses.me.TrackerResponse
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse3
import com.programmer.finalproject.model.login.LoginRequest
import com.programmer.finalproject.model.login.LoginResponse
import com.programmer.finalproject.model.notification.NotificationResponse
import com.programmer.finalproject.model.notification.UpdateNotifResponse
import com.programmer.finalproject.model.otp.OTPRequest
import com.programmer.finalproject.model.otp.OTPResponse
import com.programmer.finalproject.model.payment.HistoryPaymentResponse
import com.programmer.finalproject.model.payment.OrderRequest
import com.programmer.finalproject.model.payment.OrderResponse
import com.programmer.finalproject.model.payment.order.PutOrderRequest
import com.programmer.finalproject.model.register.RegisterRequest
import com.programmer.finalproject.model.register.RegisterResponse
import com.programmer.finalproject.model.tracker.GetTrackerByIdResponse
import com.programmer.finalproject.model.tracker.PutTrackerByIdResponse
import com.programmer.finalproject.model.tracker.PutTrackerRequest
import com.programmer.finalproject.model.tracker.TrackerRequest
import com.programmer.finalproject.model.user.password.ChangePasswordRequest
import com.programmer.finalproject.model.user.password.ChangePasswordResponse
import com.programmer.finalproject.model.user.password.ResetPasswordRequest
import com.programmer.finalproject.model.user.password.ResetPasswordResponse
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
    fun getCourses(
        @Query("category") categoryFilter: String?
    ): Call<CoursesResponse>

    @GET("categories")
    fun getCategories(): Call<CategoryResponse>

    @GET("courses")
    suspend fun getAllCourses(
        @Query("filter") recFilter: String?,
        @Query("category") categoryFilter: String?,
        @Query("level") levelFilter: String?,
        @Query("type") type: String?
    ): Response<AllCoursesResponse2>

    @GET("courses/{courseId}")
    suspend fun getCourseById(
        @Path("courseId") courseId: String
    ): Response<DetailCourseResponse3>

    @GET("user")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
    ): Response<ProfileResponse>

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

    ): Response<ChangePasswordResponse>

    @GET("orders")
    fun getHistoryPayment(
        @Header("Authorization") token: String,
    ): Call<HistoryPaymentResponse>

    @GET("courses")
    fun getCourseByName(
        @Query("name") name: String
    ): Call<CoursesResponse>

    @POST("reset/password")
    fun resetPassword(
        @Body resetPasswordResponse: ResetPasswordRequest,
    ): Call<ResetPasswordResponse>

    @POST("orders")
    fun orderCourses(
        @Header("Authorization") token: String,
        @Body orderRequest: OrderRequest
    ): Call<OrderResponse>

    @GET("otp")
    fun getOTP(
        @Header("Authorization") accessToken: String,
    ): Call<OTPResponse>

    @POST("otp")
    fun postOTP(
        @Header("Authorization") accessToken: String,
        @Body otpRequest: OTPRequest
    ): Call<OTPResponse>

    @GET("trackers")
    fun getTrackerClass(
        @Header("Authorization") token: String,
        @Query("progress") progress: String
    ): Call<TrackerResponse>

    @GET("notifications")
    fun getNotification(
        @Header("Authorization") token: String,
    ): Call<NotificationResponse>


    @PUT("notifications/{notif_id}")
    fun readNotification(
        @Header("Authorization") token: String,
        @Path("notif_id") notif_id: String
    ): Call<UpdateNotifResponse>

    @PUT("orders/{order_id}")
    fun putOrder(
        @Header("Authorization") token: String,
        @Path("order_id") order_id: String,
        @Body putOrderRequest: PutOrderRequest
    ): Call<OrderResponse>

    @GET("chapters/{chapterId}")
    suspend fun getChaptersById(
        @Path("chapterId") chapterId: String
    ): Response<ChapterResponse>

    @POST("trackers")
    suspend fun postTracker(
        @Header("Authorization") token: String,
        @Body trackerRequest: TrackerRequest
    ): Response<TrackerResponse>

    @GET("trackers/{course_id}")
    suspend fun getTrackerById(
        @Path("course_id") courseId: String
    ): Response<GetTrackerByIdResponse>

    @PUT("trackers/{course_id}")
    suspend fun putTrackerById(
        @Path("course_id") courseId: String,
        @Body putTrackerRequest: PutTrackerRequest
    ): Response<PutTrackerByIdResponse>

}