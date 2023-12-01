package com.programmer.finalproject.ui.fragment.beranda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.di.ApiRepository
import com.programmer.finalproject.model.courses.CategoryResponse
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
    val _getListCategory = MutableLiveData<CategoryResponse?>()

    val getListCourses: LiveData<CoursesResponse?>
        get() = _getListCourses

    val getListCategory: LiveData<CategoryResponse?>
        get() = _getListCategory


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

    fun getCategories() {
        loadingState.postValue(true)
        errorState.postValue(Pair(false,null))
        apiRepository.getListCategory().enqueue(
            object : Callback<CategoryResponse> {
                override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                    viewModelScope.launch {
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }
                override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {

                    viewModelScope.launch {
                        if (response.code() == 400|| response.code() == 401||response.code()==500) {
                            isError.postValue(true)
                        } else {
                            isError.postValue(false)
                            _getListCategory.postValue(response.body())

                        }
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }
            }
        )
    }

}