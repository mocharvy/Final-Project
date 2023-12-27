package com.programmer.finalproject.data

import com.programmer.finalproject.di.ApiService
import com.programmer.finalproject.model.chapter.ChapterResponse
import com.programmer.finalproject.model.courses.AllCoursesResponse2
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse3
import com.programmer.finalproject.model.user.password.ChangePasswordRequest
import com.programmer.finalproject.model.user.password.ChangePasswordResponse
import com.programmer.finalproject.model.user.update.ProfileResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getAllCourse(
        recFilter: String?,
        categoryFilter: String?,
        levelFilter: String?,
        type: String?
    ): Response<AllCoursesResponse2> {
        return apiService.getAllCourses(
            recFilter = recFilter,
            categoryFilter = categoryFilter,
            levelFilter = levelFilter,
            type = type
        )
    }

    suspend fun getCourseById(courseId: String): Response<DetailCourseResponse3> {
        return apiService.getCourseById(courseId)
    }

    suspend fun getUserProfile(token: String): Response<ProfileResponse> {
        return apiService.getUserProfile(token)
    }

    suspend fun changePassword(
        token: String,
        changePasswordRequest: ChangePasswordRequest
    ): Response<ChangePasswordResponse> {
        return apiService.changePassword(token, changePasswordRequest)
    }

//    suspend fun getHistoryPayment(token: String): Response<OrdersResponse> {
//        return apiService.getHistoryPayment(token)
//    }

    suspend fun getChapterById(chapterId: String): Response<ChapterResponse> {
        return apiService.getChaptersById(chapterId)
    }


}