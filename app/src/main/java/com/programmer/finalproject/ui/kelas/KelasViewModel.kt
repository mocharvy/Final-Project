package com.programmer.finalproject.ui.kelas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.di.ApiRepository
import com.programmer.finalproject.model.courses.me.TrackerResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class KelasViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Pair<Boolean, Exception?>>()
    var isError = MutableLiveData<Boolean>()


    val _getListTrackerClass= MutableLiveData<TrackerResponse?>()

    val getListTrackerClass: LiveData<TrackerResponse?>
        get() = _getListTrackerClass


    fun getTrackerClass(token: String, progress: String) {
        loadingState.postValue(true)
        errorState.postValue(Pair(false, null))
        apiRepository.getTrackerClass(token, progress).enqueue(
            object : Callback<TrackerResponse> {
                override fun onFailure(call: Call<TrackerResponse>, t: Throwable) {
                    viewModelScope.launch {
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }

                override fun onResponse(
                    call: Call<TrackerResponse>,
                    response: Response<TrackerResponse>
                ) {

                    viewModelScope.launch {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            isError.postValue(true)
                        } else {
                            _getListTrackerClass.postValue(response.body())

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