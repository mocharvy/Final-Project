package com.programmer.finalproject.database.user

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.programmer.finalproject.database.course.Course
import com.programmer.finalproject.utils.UserTypeConverter

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(UserTypeConverter::class)
abstract class UserDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao
}