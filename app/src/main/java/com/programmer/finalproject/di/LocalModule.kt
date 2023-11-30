package com.programmer.finalproject.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
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

}