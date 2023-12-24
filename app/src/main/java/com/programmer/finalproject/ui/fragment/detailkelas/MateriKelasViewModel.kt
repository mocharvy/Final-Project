package com.programmer.finalproject.ui.fragment.detailkelas

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.data.Repository
import com.programmer.finalproject.model.chapter.ChapterResponse
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MateriKelasViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    /** RETROFIT **/

    val detailChapterResponse: MutableLiveData<NetworkResult<ChapterResponse>> =
        MutableLiveData()

    fun getDetailChapter(chapterId: String) = viewModelScope.launch {
        getDetailChapterSafeCall(chapterId)
    }

    private suspend fun getDetailChapterSafeCall(chapterId: String) {
        detailChapterResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getChapterById(chapterId)
                detailChapterResponse.value = handleDetailCourseResponse(response)

            } catch (e: Exception) {
                detailChapterResponse.value = NetworkResult.Error("Error: $e")
            }
        } else {
            detailChapterResponse.value = NetworkResult.Error("No internet connection")
        }
    }

    private fun handleDetailCourseResponse(response: Response<ChapterResponse>): NetworkResult<ChapterResponse> {
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
                val detailChapter = response.body()
                return NetworkResult.Success(detailChapter!!)
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