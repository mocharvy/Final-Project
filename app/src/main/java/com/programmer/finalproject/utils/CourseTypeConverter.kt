package com.programmer.finalproject.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.programmer.finalproject.model.courses.AllCoursesResponse2

class CourseTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun courseToString(listAllCoursesResponse: AllCoursesResponse2): String{
        return gson.toJson(listAllCoursesResponse)
    }

    @TypeConverter
    fun stringToCourse(data: String): AllCoursesResponse2 {
        val listType = object : TypeToken<AllCoursesResponse2>() {}.type
        return gson.fromJson(data, listType)
    }
}