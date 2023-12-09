package com.programmer.finalproject.data

import com.programmer.finalproject.di.ApiService
import com.programmer.finalproject.model.courses.AllCoursesResponse
import com.programmer.finalproject.model.courses.AllCoursesResponse2
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse
import com.programmer.finalproject.model.user.UserDetailResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getAllCourse(): Response<AllCoursesResponse2> {
        return apiService.getAllCourses()
    }

    suspend fun getCourseById(courseId : String): Response<DetailCourseResponse> {
        return apiService.getCourseById(courseId)
    }

    suspend fun getUserProfile(token: String): Response<UserDetailResponse> {
        return apiService.getUserProfile(token)
    }
}