package com.programmer.finalproject.database.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.programmer.finalproject.model.user.UserDetailResponse
import com.programmer.finalproject.model.user.update.ProfileResponse

@Entity(tableName = "user_table2")
class User(
    var userDetailResponse: ProfileResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}