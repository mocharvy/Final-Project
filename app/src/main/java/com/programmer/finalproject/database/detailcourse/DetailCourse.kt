package com.programmer.finalproject.database.detailcourse

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse3

@Entity(tableName = "detail_course_table3")
class DetailCourse(
    var detailCourseResponse: DetailCourseResponse3
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}