package com.programmer.finalproject.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.programmer.finalproject.model.user.UserDetailResponse
import com.programmer.finalproject.model.user.update.ProfileRequest
import com.programmer.finalproject.model.user.update.ProfileResponse

class UserTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun userDetailToString(userDetailResponse: ProfileResponse): String {
        return gson.toJson(userDetailResponse)
    }

    @TypeConverter
    fun stringToDetailCourse(data: String): ProfileResponse {
        val listType = object : TypeToken<ProfileResponse>() {}.type
        return gson.fromJson(data, listType)
    }
}