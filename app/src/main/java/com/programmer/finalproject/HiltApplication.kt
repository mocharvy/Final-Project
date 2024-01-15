package com.programmer.finalproject

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.programmer.finalproject.utils.NOTIFICATION_CHANNEL_ID
import com.programmer.finalproject.utils.NOTIFICATION_CHANNEL_NAME
import com.programmer.finalproject.utils.NightMode
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class HiltApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.getString(
            getString(R.string.pref_key_dark),
            getString(R.string.pref_dark_auto)
        )?.apply {
            val mode = NightMode.valueOf(this.uppercase(Locale.US))
            AppCompatDelegate.setDefaultNightMode(mode.value)
        }

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = NOTIFICATION_CHANNEL_NAME
            val descriptionText = "Raih impianmu dengan belajar setiap hari"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}