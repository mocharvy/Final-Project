package com.programmer.finalproject.di

import com.programmer.finalproject.model.login.LoginRequest
import com.programmer.finalproject.model.otp.OTPRequest
import com.programmer.finalproject.model.payment.OrderRequest
import com.programmer.finalproject.model.payment.order.PutOrderRequest
import com.programmer.finalproject.model.register.RegisterRequest
import com.programmer.finalproject.model.user.password.ResetPasswordRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ApiHelper @Inject constructor(private val api: ApiService) {
    fun register(registerRequest: RegisterRequest) = api.registerUser(registerRequest)
    fun login(loginRequest: LoginRequest) = api.loginUser(loginRequest)
    fun getCourses(categoryFilter:String) = api.getCourses(categoryFilter)
    fun getCategories() = api.getCategories()

    suspend fun editProfile(name:RequestBody,email:RequestBody,phone_number:RequestBody,country:RequestBody,city:RequestBody,photo:MultipartBody.Part,token:String) =
        api.editProfile(name,email,phone_number,country,city,photo,token)

    fun getHistoryPayment(token: String) = api.getHistoryPayment(token)
    fun getCoursesByName(name:String) = api.getCourseByName(name)

    fun resetPassword(resetPasswordRequest: ResetPasswordRequest)= api.resetPassword(resetPasswordRequest)

    fun orderCourses(token: String,orderRequest: OrderRequest) = api.orderCourses(token,orderRequest)
    fun postOTP(accessToken : String,otpRequest: OTPRequest) = api.postOTP(accessToken,otpRequest)
    fun getOTP(accessToken : String) = api.getOTP(accessToken)

    fun getTrackerClass(token : String,progress:String) = api.getTrackerClass(token,progress)
    fun getNotification(token : String) = api.getNotification(token)
    fun readNotification(token : String,notif_id : String) = api.readNotification(token,notif_id)
    fun putOrder(token : String,order_id:String,putOrder : PutOrderRequest) = api.putOrder(token,order_id,putOrder)

}