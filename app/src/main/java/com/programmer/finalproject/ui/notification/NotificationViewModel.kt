package com.programmer.finalproject.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.di.ApiRepository
import com.programmer.finalproject.model.courses.me.TrackerResponse
import com.programmer.finalproject.model.notification.NotificationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel  @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Pair<Boolean, Exception?>>()
    var isError = MutableLiveData<Boolean>()


    val _getNotification= MutableLiveData<NotificationResponse?>()

    val getNotification: LiveData<NotificationResponse?>
        get() = _getNotification


    fun getNotification(token: String) {
        loadingState.postValue(true)
        errorState.postValue(Pair(false, null))
        apiRepository.getNotification(token).enqueue(
            object : Callback<NotificationResponse> {
                override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                    viewModelScope.launch {
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }

                override fun onResponse(
                    call: Call<NotificationResponse>,
                    response: Response<NotificationResponse>
                ) {

                    viewModelScope.launch {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            isError.postValue(true)
                        } else {
                            _getNotification.postValue(response.body())

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