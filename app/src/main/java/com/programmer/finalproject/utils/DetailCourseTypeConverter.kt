package com.programmer.finalproject.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse3

class DetailCourseTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun detailCourseToString(detailCourseResponse: DetailCourseResponse3): String{
        return gson.toJson(detailCourseResponse)
    }

    @TypeConverter
    fun stringToDetailCourse(data: String): DetailCourseResponse3 {
        val listType = object : TypeToken<DetailCourseResponse3>() {}.type
        return gson.fromJson(data, listType)
    }
}