package com.programmer.finalproject.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.programmer.finalproject.model.user.UserDetailResponse

class UserTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun userDetailToString(userDetailResponse: UserDetailResponse): String {
        return gson.toJson(userDetailResponse)
    }

    @TypeConverter
    fun stringToDetailCourse(data: String): UserDetailResponse {
        val listType = object : TypeToken<UserDetailResponse>() {}.type
        return gson.fromJson(data, listType)
    }
}