package com.programmer.finalproject.ui.fragment.detailkelas

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.data.Repository
import com.programmer.finalproject.database.detailcourse.DetailCourse
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailKelasViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    val courseId = MutableLiveData<String>()

    /** ROOM DATABASE **/

    val readDetailCourse : LiveData<List<DetailCourse>> = repository.local.readDetailCourse(courseId.toString()).asLiveData()

    private fun insertDetailCourse(detailCourse: DetailCourse) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertDetailCourse(detailCourse)
        }



    /** RETROFIT **/

    val detailCourseResponse: MutableLiveData<NetworkResult<DetailCourseResponse>> =
        MutableLiveData()

    fun getDetailCourse(courseId: String) = viewModelScope.launch {
        getDetailCourseSafeCall(courseId)
    }

    private suspend fun getDetailCourseSafeCall(courseId: String) {
        detailCourseResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getCourseById(courseId)
                detailCourseResponse.value = handleDetailCourseResponse(response)

                val detailCourse = detailCourseResponse.value!!.data
                if (detailCourse != null) {
                    offlineCacheDetailCourse(detailCourse)
                }
            } catch (e: Exception) {
                detailCourseResponse.value = NetworkResult.Error("Error: $e")
            }
        } else {
            detailCourseResponse.value = NetworkResult.Error("No internet connection")
        }
    }

    private fun handleDetailCourseResponse(response: Response<DetailCourseResponse>): NetworkResult<DetailCourseResponse> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 500 -> {
                return NetworkResult.Error("Server Error")
            }

            response.code() == 400 -> {
                return NetworkResult.Error("Client Error")
            }

            response.isSuccessful -> {
                val detailCourse = response.body()
                return NetworkResult.Success(detailCourse!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun offlineCacheDetailCourse(listDetailCourse: DetailCourseResponse) {
        val detailCourse = DetailCourse(listDetailCourse)
        insertDetailCourse(detailCourse)
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