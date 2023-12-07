package com.programmer.finalproject.database.detailcourse

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.programmer.finalproject.utils.DetailCourseTypeConverter

@Database(
    entities = [DetailCourse::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(DetailCourseTypeConverter::class)
abstract class DetailCourseDatabase: RoomDatabase() {
    abstract  fun detailCourseDao(): DetailCourseDao
}