package com.programmer.finalproject.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse

class DetailCourseTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun detailCourseToString(detailCourseResponse: DetailCourseResponse): String{
        return gson.toJson(detailCourseResponse)
    }

    @TypeConverter
    fun stringToDetailCourse(data: String): DetailCourseResponse {
        val listType = object : TypeToken<DetailCourseResponse>() {}.type
        return gson.fromJson(data, listType)
    }
}