package com.programmer.finalproject.database.course

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.programmer.finalproject.model.courses.AllCoursesResponse

@Entity(tableName = "courses_table")
class Course(
    var listAllCoursesResponse: AllCoursesResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}