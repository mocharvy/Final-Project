package com.programmer.finalproject.ui.fragment.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.di.ApiRepository
import com.programmer.finalproject.model.login.LoginRequest
import com.programmer.finalproject.model.login.LoginResponse
import com.programmer.finalproject.model.register.RegisterRequest
import com.programmer.finalproject.model.register.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {

    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Pair<Boolean, Exception?>>()
    var isError = MutableLiveData<Boolean>()
    var verified = MutableLiveData<Boolean>()

    val _accountData = MutableLiveData<LoginResponse?>()
    val accountData: MutableLiveData<LoginResponse?>
        get() = _accountData


    fun login(loginRequest: LoginRequest) {
        loadingState.postValue(true)
        errorState.postValue(Pair(false, null))
        apiRepository.login(loginRequest).enqueue(
            object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    viewModelScope.launch {
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val addedUser = response.body()
                    if (response.code() == 200) {
                        viewModelScope.launch {
                            verified.postValue(true)
                            isError.postValue(false)
                        }
                    } else if (response.code() == 401) {
                        isError.postValue(true)
                    }
                    viewModelScope.launch {
                        _accountData.postValue(addedUser)
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }
            }
        )
    }
    fun register(registerRequest: RegisterRequest) {
        loadingState.postValue(true)
        errorState.postValue(Pair(false, null))
        apiRepository.register(registerRequest).enqueue(
            object : Callback<RegisterResponse> {
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    viewModelScope.launch {
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    val addedUser = response.body()
                    if (response.code() == 200) {
                        viewModelScope.launch {
                            verified.postValue(true)
                            isError.postValue(false)
                        }
                    } else if (response.code() == 401) {
                        isError.postValue(true)
                    }
                    viewModelScope.launch {
//                        _accountData.postValue(addedUser)
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }
            }
        )
    }

}