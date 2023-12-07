package com.programmer.finalproject.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.programmer.finalproject.model.courses.AllCoursesResponse

class CourseTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun courseToString(listAllCoursesResponse: AllCoursesResponse): String{
        return gson.toJson(listAllCoursesResponse)
    }

    @TypeConverter
    fun stringToCourse(data: String): AllCoursesResponse {
        val listType = object : TypeToken<AllCoursesResponse>() {}.type
        return gson.fromJson(data, listType)
    }
}