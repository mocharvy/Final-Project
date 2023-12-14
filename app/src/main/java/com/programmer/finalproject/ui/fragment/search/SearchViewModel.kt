package com.programmer.finalproject.ui.fragment.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.programmer.finalproject.di.ApiRepository
import com.programmer.finalproject.model.courses.Courses
import com.programmer.finalproject.model.courses.CoursesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {
    private var mSearchCoursesLiveData = MutableLiveData<List<Courses>>()

    fun searchCourses(name: String) =
        apiRepository.getCoursesByame(name)
            .enqueue(object : Callback<CoursesResponse> {
                override fun onResponse(call: Call<CoursesResponse>, response: Response<CoursesResponse>) {
                    val mealList = response.body()?.data
                    mealList?.let {
                        mSearchCoursesLiveData.postValue(it)
                        Log.d("search", it.toString())

                    }
                }

                override fun onFailure(call: Call<CoursesResponse>, t: Throwable) {
                    Log.d("Courses Search ", t.message.toString())
                }
            })
    fun observeSearchCoursesLiveData(): LiveData<List<Courses>> = mSearchCoursesLiveData


}