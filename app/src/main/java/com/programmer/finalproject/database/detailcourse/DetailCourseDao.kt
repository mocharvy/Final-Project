package com.programmer.finalproject.database.detailcourse

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailCourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailCourse(detailCourse: DetailCourse)

    @Query("SELECT * FROM detail_course_table3 WHERE id= :courseId ")
    fun readDetailCourse(courseId: String): Flow<List<DetailCourse>>
}