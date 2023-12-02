package com.programmer.finalproject.database.course

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.programmer.finalproject.utils.CourseTypeConverter

@Database(
    entities = [Course::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(CourseTypeConverter::class)
abstract class CourseDataBase: RoomDatabase() {
    abstract fun courseDao(): CourseDao
}