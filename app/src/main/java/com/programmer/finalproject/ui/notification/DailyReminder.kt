package com.programmer.finalproject.ui.notification

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.programmer.finalproject.MainActivity
import com.programmer.finalproject.R
import com.programmer.finalproject.utils.ID_REPEATING
import com.programmer.finalproject.utils.NOTIFICATION_CHANNEL_ID
import com.programmer.finalproject.utils.NOTIFICATION_ID
import com.programmer.finalproject.utils.executeThread
import java.util.Calendar

class DailyReminder : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        executeThread {
            showNotification(context)
        }
    }

    //TODO 12 : Implement daily reminder for every 06.00 a.m using AlarmManager
    fun setDailyReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminder::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            100,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            pendingIntent
        )

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 2)
        calendar.set(Calendar.MINUTE, 5)
        calendar.set(Calendar.SECOND, 0)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminder::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ID_REPEATING,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }

    fun showNotification(context: Context) {
        val notificationStyle = NotificationCompat.InboxStyle()
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.pref_notify_name))
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notification)
            .setStyle(notificationStyle)
            .build()

        notificationManager.notify(NOTIFICATION_ID, builder)
    }
}