package com.programmer.finalproject.ui.fragment.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.data.Repository
import com.programmer.finalproject.di.ApiRepository
import com.programmer.finalproject.model.login.LoginRequest
import com.programmer.finalproject.model.login.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    private val apiRepository: ApiRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse>
        get() = _loginResponse

    private val _errorState = MutableLiveData<Throwable>()
    val errorState: LiveData<Throwable>
        get() = _errorState

    private val _token = MutableLiveData<String?>()
    val token: LiveData<String?>
        get() = _token

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean>
        get() = _isLogin

    init {
        viewModelScope.launch {
            _token.value = repository.local.readTokenFromDataStore()
            _isLogin.value = repository.local.readLoginStateFromDataStore()
        }
    }

    fun login(loginRequest: LoginRequest) {
        apiRepository.login(loginRequest).enqueue(
            object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    _errorState.postValue(t)
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        _loginResponse.postValue(response.body())
                        val addedUser = response.body()
                        viewModelScope.launch {
                            addedUser?.data?.accessToken.let {
                                saveTokenAndLoginState(it!!, true)
                            }
                        }

                    } else {
                        _errorState.postValue(Throwable("Login failed."))
                    }
                }
            }
        )
    }

    fun logout() {
        viewModelScope.launch {
            clearUserData()
            _token.value = null
            _isLogin.value = false
        }
    }

    private suspend fun clearUserData() {
        repository.local.clearUserData()
    }

    private suspend fun saveTokenAndLoginState(accessToken: String, isLogin: Boolean) {
        _token.value = accessToken
        _isLogin.value = isLogin
        repository.local.saveTokenToDataStore(accessToken)
        repository.local.saveLoginStateToDataStore(isLogin)
    }


}