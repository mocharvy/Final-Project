package com.programmer.finalproject.database.course

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.programmer.finalproject.model.courses.AllCoursesResponse2

@Entity(tableName = "courses_table_fix")
class Course(
    var listAllCoursesResponse: AllCoursesResponse2
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}