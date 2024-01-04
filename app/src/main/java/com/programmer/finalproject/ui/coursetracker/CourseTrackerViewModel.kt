package com.programmer.finalproject.ui.coursetracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.data.Repository
import com.programmer.finalproject.model.courses.me.TrackerResponse
import com.programmer.finalproject.model.tracker.GetTrackerByIdResponse
import com.programmer.finalproject.model.tracker.PutTrackerByIdResponse
import com.programmer.finalproject.model.tracker.PutTrackerRequest
import com.programmer.finalproject.model.tracker.TrackerRequest
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CourseTrackerViewModel @Inject constructor(
    val repository: Repository,
    application: Application
): AndroidViewModel(application) {

    private val _postTrackerResult = MutableLiveData<NetworkResult<TrackerResponse>>()
    val postTrackerResult: LiveData<NetworkResult<TrackerResponse>> = _postTrackerResult

    private val _getTrackerResult = MutableLiveData<NetworkResult<GetTrackerByIdResponse>>()
    val getTrackerResult: LiveData<NetworkResult<GetTrackerByIdResponse>> = _getTrackerResult

    private val _putTrackerResult = MutableLiveData<NetworkResult<PutTrackerByIdResponse>>()
    val putTrackerResult: LiveData<NetworkResult<PutTrackerByIdResponse>> = _putTrackerResult

    fun postTracker(trackerRequest: TrackerRequest) {
        viewModelScope.launch {
            _postTrackerResult.value = NetworkResult.Loading()
            try {
                val response = repository.remote.postTracker(trackerRequest)
                _postTrackerResult.value = handleResponse(response)
            } catch (e: Exception) {
                _postTrackerResult.value = NetworkResult.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun getTrackerById(courseId: String) {
        viewModelScope.launch {
            _getTrackerResult.value = NetworkResult.Loading()
            try {
                val response = repository.remote.getTrackerById(courseId)
                _getTrackerResult.value = handleResponse(response)
            } catch (e: Exception) {
                _getTrackerResult.value = NetworkResult.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun putTrackerById(courseId: String, putTrackerRequest: PutTrackerRequest) {
        viewModelScope.launch {
            _putTrackerResult.value = NetworkResult.Loading()
            try {
                val response = repository.remote.putTrackerById(courseId, putTrackerRequest)
                _putTrackerResult.value = handleResponse(response)
            } catch (e: Exception) {
                _putTrackerResult.value = NetworkResult.Error(e.message ?: "An error occurred")
            }
        }
    }

    private fun <T> handleResponse(response: Response<T>): NetworkResult<T> {
        return if (response.isSuccessful) {
            NetworkResult.Success(response.body())
        } else {
            NetworkResult.Error(response.message())
        }
    }

}