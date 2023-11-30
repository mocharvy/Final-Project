package com.programmer.finalproject.ui.fragment.beranda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.di.ApiRepository
import com.programmer.finalproject.model.courses.CoursesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BerandaViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Pair<Boolean, Exception?>>()
    var isError = MutableLiveData<Boolean>()


    val _getListCourses = MutableLiveData<CoursesResponse?>()

    val getListCourses: LiveData<CoursesResponse?>
        get() = _getListCourses

    fun getCourses() {
        loadingState.postValue(true)
        errorState.postValue(Pair(false,null))
        apiRepository.getCourses().enqueue(
            object : Callback<CoursesResponse> {
                override fun onFailure(call: Call<CoursesResponse>, t: Throwable) {
                    viewModelScope.launch {
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }
                override fun onResponse(call: Call<CoursesResponse>, response: Response<CoursesResponse>) {

                    viewModelScope.launch {
                        if (response.code() == 400|| response.code() == 401||response.code()==500) {
                            isError.postValue(true)
                        } else {
                            isError.postValue(false)
                            _getListCourses.postValue(response.body())

                        }
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }
            }
        )
    }

}