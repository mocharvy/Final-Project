package com.programmer.finalproject.database.detailcourse

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse

@Entity(tableName = "detail_course_table")
class DetailCourse(
    var detailCourseResponse: DetailCourseResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}