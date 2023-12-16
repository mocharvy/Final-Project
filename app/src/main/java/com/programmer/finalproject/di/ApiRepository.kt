package com.programmer.finalproject.di

import com.programmer.finalproject.model.login.LoginRequest
import com.programmer.finalproject.model.otp.OTPRequest
import com.programmer.finalproject.model.payment.OrderRequest
import com.programmer.finalproject.model.register.RegisterRequest
import com.programmer.finalproject.model.user.password.ResetPasswordRequest
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

    fun getHistoryPayment(token: String) = apiHelper.getHistoryPayment(token)

    fun getCoursesByame(name:String) = apiHelper.getCoursesByName(name)
    fun resetPassword(resetPasswordRequest: ResetPasswordRequest) = apiHelper.resetPassword(resetPasswordRequest)

    fun orderCourses(token: String,orderRequest: OrderRequest) = apiHelper.orderCourses(token,orderRequest)

    fun postOTP(accessToken:String,otpRequest: OTPRequest) = apiHelper.postOTP(accessToken,otpRequest)
    fun getOTP(accessToken:String) = apiHelper.getOTP(accessToken)

    fun getTrackerClass(token : String,progress:String) = apiHelper.getTrackerClass(token,progress)

}