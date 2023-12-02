package com.programmer.finalproject.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.programmer.finalproject.database.course.CourseDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
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

    /** Curse Database **/
    @Singleton
    @Provides
    fun provideCourseDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CourseDataBase::class.java,
        "course_database"
    ).build()

    @Singleton
    @Provides
    fun provideCourseDao(courseDataBase: CourseDataBase) = courseDataBase.courseDao()

}