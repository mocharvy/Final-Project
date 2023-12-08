package com.programmer.finalproject.database.course

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(course: Course)

    @Query("SELECT * FROM courses_table_fix ORDER BY id ASC")
    fun readCourses(): Flow<List<Course>>
}