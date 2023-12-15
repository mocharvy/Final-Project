package com.programmer.finalproject.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.programmer.finalproject.database.course.Course
import com.programmer.finalproject.database.course.CourseDao
import com.programmer.finalproject.database.detailcourse.DetailCourse
import com.programmer.finalproject.database.detailcourse.DetailCourseDao
import com.programmer.finalproject.database.user.User
import com.programmer.finalproject.database.user.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val courseDao: CourseDao,
    private val detailCourseDao: DetailCourseDao,
    private val userDao: UserDao,
    private val dataStore: DataStore<Preferences>
) {

    /** COURSE **/
    fun readCourse(): Flow<List<Course>> {
        return courseDao.readCourses()
    }

    suspend fun insertCourse(course: Course) {
        courseDao.insertCourses(course)
    }


    /** DETAIL COURSE **/

    suspend fun insertDetailCourse(detailCourse: DetailCourse) {
        detailCourseDao.insertDetailCourse(detailCourse)
    }

    fun readDetailCourse(courseId: String): Flow<List<DetailCourse>> {
        return detailCourseDao.readDetailCourse(courseId)
    }

    /** USER **/

    fun readUserDetail(id: String): Flow<List<User>> {
        return userDao.readUserDetail(id)
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    /** USER AUTH **/

    suspend fun saveTokenToDataStore(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOKEN] = token
        }
    }

    suspend fun saveLoginStateToDataStore(isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_LOGIN] = isLogin
        }
    }

    suspend fun readTokenFromDataStore(): String? {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.TOKEN]
        }.firstOrNull()
    }

    suspend fun readLoginStateFromDataStore(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.IS_LOGIN] ?: false
        }.firstOrNull() ?: false
    }

    private object PreferencesKeys {
        val TOKEN = stringPreferencesKey("token")
        val IS_LOGIN = booleanPreferencesKey("is_login")
    }

    suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.TOKEN)
            preferences[PreferencesKeys.IS_LOGIN] = false
        }
    }

}