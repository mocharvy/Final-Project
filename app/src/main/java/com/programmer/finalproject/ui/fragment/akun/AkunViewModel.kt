package com.programmer.finalproject.ui.fragment.akun

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.data.Repository
import com.programmer.finalproject.database.user.User
import com.programmer.finalproject.model.user.UserDetailResponse
import com.programmer.finalproject.model.user.update.ProfileRequest
import com.programmer.finalproject.model.user.update.ProfileResponse
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AkunViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** Room Database **/

    //val readUser: LiveData<List<User>> = repository.local.readUserDetail().asLiveData()

    private fun insertUser(user: User) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertUser(user)
        }


    /** Retrofit **/

    var userDetailResponse: MutableLiveData<NetworkResult<ProfileResponse>> =
        MutableLiveData()

    fun getUserDetail(token: String) = viewModelScope.launch {
        getUserDetailSafeCall(token)
    }

    private suspend fun getUserDetailSafeCall(token: String) {
        userDetailResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getUserProfile(token)
                userDetailResponse.value = handleUserDetailResponse(response)

                val userDetail = userDetailResponse.value!!.data
                if (userDetail != null) {
                    offlineCacheUser(userDetail)
                }
            } catch (e: Exception) {
                userDetailResponse.value = NetworkResult.Error("Error: $e")
            }
        } else {
            userDetailResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun offlineCacheUser(userDetail: ProfileResponse) {
        val user = User(userDetail)
        insertUser(user)
    }

    private fun handleUserDetailResponse(response: Response<ProfileResponse>): NetworkResult<ProfileResponse> {
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