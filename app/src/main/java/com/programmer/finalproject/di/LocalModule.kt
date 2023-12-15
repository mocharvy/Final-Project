package com.programmer.finalproject.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.programmer.finalproject.database.course.CourseDataBase
import com.programmer.finalproject.database.detailcourse.DetailCourseDatabase
import com.programmer.finalproject.database.user.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideSharedPrefs(application: Application): SharedPreferences? {
        //nanti di ubah ke companion object di login
        val LOGIN_SHARED_PREF = ""
        return application.getSharedPreferences(
            LOGIN_SHARED_PREF,
            Context.MODE_PRIVATE
        )
    }

    /** Course Database **/
    @Singleton
    @Provides
    fun provideCourseDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CourseDataBase::class.java,
        "course_database_fix"
    ).build()

    @Singleton
    @Provides
    fun provideCourseDao(courseDataBase: CourseDataBase) = courseDataBase.courseDao()


    /** Detail Course Database **/
    @Singleton
    @Provides
    fun provideDetailCourseDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        DetailCourseDatabase::class.java,
        "detail_course_database_fix"
    ).build()

    @Singleton
    @Provides
    fun provideDetailCourseDao(detailCourseDatabase: DetailCourseDatabase) =
        detailCourseDatabase.detailCourseDao()


    /** User Database **/

    @Singleton
    @Provides
    fun provideUserDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        UserDatabase::class.java,
        "user_database_fix"
    ).build()

    @Singleton
    @Provides
    fun provideUserDao(userDatabase: UserDatabase) =
        userDatabase.userDao()

    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext appContext: Context
    ): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                appContext.preferencesDataStoreFile("settings")
            }
        )

}