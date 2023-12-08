package com.programmer.finalproject.ui.fragment.kursus

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.data.Repository
import com.programmer.finalproject.database.course.Course
import com.programmer.finalproject.model.courses.AllCoursesResponse2
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class KursusViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** Room Database **/

    val readCourse: LiveData<List<Course>> = repository.local.readCourse().asLiveData()

    private fun insertCourse(course: Course) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertCourse(course)
        }


    /** Retrofit **/

    var listAllCoursesResponse: MutableLiveData<NetworkResult<AllCoursesResponse2>> =
        MutableLiveData()

    fun getListCourse() = viewModelScope.launch {
        getListCourseSafeCall()
    }

    private suspend fun getListCourseSafeCall() {
        listAllCoursesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getAllCourse()
                listAllCoursesResponse.value = handleAllCourseResponse(response)

                val listCourse = listAllCoursesResponse.value!!.data
                if (listCourse != null) {
                    offlineCacheCourse(listCourse)
                } else {
                    Log.d("Insert database", "INSERT FAILED")
                }
            } catch (e: Exception) {
                listAllCoursesResponse.value = NetworkResult.Error("Error: $e")
            }
        } else {
            listAllCoursesResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun offlineCacheCourse(listCourse: AllCoursesResponse2) {
        val course = Course(listCourse)
        insertCourse(course)
    }

    private fun handleAllCourseResponse(response: Response<AllCoursesResponse2>): NetworkResult<AllCoursesResponse2> {
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
                val listCourse = response.body()
                return NetworkResult.Success(listCourse!!)
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