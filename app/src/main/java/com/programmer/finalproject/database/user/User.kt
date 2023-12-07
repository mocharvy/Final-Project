package com.programmer.finalproject.database.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.programmer.finalproject.model.user.UserDetailResponse

@Entity(tableName = "user_table")
class User(
    var userDetailResponse: UserDetailResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}