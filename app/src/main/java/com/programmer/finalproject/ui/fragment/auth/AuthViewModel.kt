package com.programmer.finalproject.ui.fragment.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.data.Repository
import com.programmer.finalproject.database.user.User
import com.programmer.finalproject.di.ApiRepository
import com.programmer.finalproject.model.login.LoginRequest
import com.programmer.finalproject.model.login.LoginResponse
import com.programmer.finalproject.model.register.RegisterRequest
import com.programmer.finalproject.model.register.RegisterResponse
import com.programmer.finalproject.model.user.password.ResetPasswordRequest
import com.programmer.finalproject.model.user.password.ResetPasswordResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val repository: Repository
) : ViewModel() {

    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Pair<Boolean, Exception?>>()
    var isError = MutableLiveData<Boolean>()
    var verified = MutableLiveData<Boolean>()

    val _accountData = MutableLiveData<LoginResponse?>()
    val accountData: MutableLiveData<LoginResponse?>
        get() = _accountData

    val _registerResponse = MutableLiveData<RegisterResponse?>()
    val registerResponse: MutableLiveData<RegisterResponse?>
        get() = _registerResponse

    //=========AUTH==========//
    private val _token = MutableLiveData<String?>()
    val token: LiveData<String?>
        get() = _token

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean>
        get() = _isLogin
    //=========AUTH==========//

    init {
        viewModelScope.launch {
            _token.value = repository.local.readTokenFromDataStore()
            _isLogin.value = repository.local.readLoginStateFromDataStore()
        }
    }


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

                            addedUser?.data?.accessToken?.let {
                                _token.value = it
                                _isLogin.value = true
                                saveTokenAndLoginState(it, true)
                            }
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
                    val register = response.body()
                    if (response.code() == 200) {
                        viewModelScope.launch {
                            verified.postValue(true)
                            isError.postValue(false)
                            _registerResponse.postValue(register)

                        }
                    } else if (response.code() == 401) {
                        isError.postValue(true)
                    }
                    viewModelScope.launch {
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }
            }
        )
    }

    fun resetPassword(resetPasswordRequest: ResetPasswordRequest) {
        loadingState.postValue(true)
        errorState.postValue(Pair(false, null))
        apiRepository.resetPassword(resetPasswordRequest).enqueue(
            object : Callback<ResetPasswordResponse> {
                override fun onFailure(call: Call<ResetPasswordResponse>, t: Throwable) {
                    viewModelScope.launch {
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }
                override fun onResponse(call: Call<ResetPasswordResponse>, response: Response<ResetPasswordResponse>) {
                     if (response.code() == 200) {
                        viewModelScope.launch {
                            verified.postValue(true)
                            isError.postValue(false)
                        }
                    } else if (response.code() == 401||response.code()==400) {
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

    fun logout() {
        _token.value = null
        _isLogin.value = false
    }

    private suspend fun saveTokenAndLoginState(accessToken: String, isLogin: Boolean) {
        repository.local.saveTokenToDataStore(accessToken)
        repository.local.saveLoginStateToDataStore(isLogin)
    }

}