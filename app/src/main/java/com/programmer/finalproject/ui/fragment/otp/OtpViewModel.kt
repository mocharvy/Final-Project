package com.programmer.finalproject.ui.fragment.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.di.ApiRepository
import com.programmer.finalproject.model.otp.OTPRequest
import com.programmer.finalproject.model.otp.OTPResponse
import com.programmer.finalproject.model.payment.HistoryPaymentResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Pair<Boolean, Exception?>>()
    var isError = MutableLiveData<Boolean>()


    val _getListHistoryPayment = MutableLiveData<HistoryPaymentResponse?>()

    val getListHistoryPayment: LiveData<HistoryPaymentResponse?>
        get() = _getListHistoryPayment


    fun postOtp(accessToken: String,otpRequest: OTPRequest) {
        loadingState.postValue(true)
        errorState.postValue(Pair(false, null))
        apiRepository.postOTP(accessToken, otpRequest).enqueue(
            object : Callback<OTPResponse> {
                override fun onFailure(call: Call<OTPResponse>, t: Throwable) {
                    viewModelScope.launch {
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }

                override fun onResponse(
                    call: Call<OTPResponse>,
                    response: Response<OTPResponse>
                ) {

                    viewModelScope.launch {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            isError.postValue(true)
                        } else {
                            isError.postValue(false)
                        }
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }
            }
        )
    }

    fun getOtp(accessToken: String) {
        loadingState.postValue(true)
        errorState.postValue(Pair(false, null))
        apiRepository.getOTP(accessToken).enqueue(
            object : Callback<OTPResponse> {
                override fun onFailure(call: Call<OTPResponse>, t: Throwable) {
                    viewModelScope.launch {
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }

                override fun onResponse(
                    call: Call<OTPResponse>,
                    response: Response<OTPResponse>
                ) {

                    viewModelScope.launch {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            isError.postValue(true)
                        } else {
                            isError.postValue(false)
                        }
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }
            }
        )
    }

}