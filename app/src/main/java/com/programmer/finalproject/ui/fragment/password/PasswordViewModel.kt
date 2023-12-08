package com.programmer.finalproject.ui.fragment.password

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.data.Repository
import com.programmer.finalproject.model.user.password.ChangePasswordRequest
import com.programmer.finalproject.model.user.password.ChangePasswordResponse
import com.programmer.finalproject.model.user.update.ProfileRequest
import com.programmer.finalproject.model.user.update.ProfileResponse
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var changePasswordResponse: MutableLiveData<NetworkResult<ChangePasswordResponse>> =
        MutableLiveData()

    fun changePassword(token: String,changePasswordRequest: ChangePasswordRequest) = viewModelScope.launch {
        changePasswordResponseSafeCall(token,changePasswordRequest)
    }

    private suspend fun changePasswordResponseSafeCall(token: String, changePasswordRequest: ChangePasswordRequest) {
        changePasswordResponse.value = NetworkResult.Loading()
        if(hasInternetConnection()){
            try {
                val response = repository.remote.changePassword(token,changePasswordRequest)
                changePasswordResponse.value = handleChangePassword(response)

                val updateProfile = changePasswordResponse.value!!.data
//                if (updateProfile != null) {
//                    offlineCacheUser(updateProfile)
//                }
            } catch (e: Exception) {
                changePasswordResponse.value = NetworkResult.Error("Error: $e")
            }
        } else {
            changePasswordResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun handleChangePassword(response: Response<ChangePasswordResponse>): NetworkResult<ChangePasswordResponse>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 500 -> {
                return NetworkResult.Error("Server Error")
            }

            response.code() == 400 -> {
                return NetworkResult.Error("User has been registered")
            }

            response.code() == 401 -> {
                return NetworkResult.Error("Unauthorized")
            }

            response.isSuccessful -> {
                val userDetail = response.body()
                return NetworkResult.Success(userDetail!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {

        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

    }
}