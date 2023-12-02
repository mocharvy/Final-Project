package com.programmer.finalproject.data

import com.programmer.finalproject.database.course.Course
import com.programmer.finalproject.database.course.CourseDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val courseDao: CourseDao
) {

    /** COURSE **/
    fun readCourse(): Flow<List<Course>> {
        return courseDao.readCourses()
    }

    suspend fun insertCourse(course: Course) {
        courseDao.insertCourses(course)
    }

}