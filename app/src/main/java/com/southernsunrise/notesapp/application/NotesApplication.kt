package com.southernsunrise.notesapp.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.southernsunrise.notesapp.di.DI
import com.southernsunrise.notesapp.notifications.NotificationChannelType

class NotesApplication : Application() {
    companion object {
        lateinit var instance: NotesApplication
            private set

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        DI.init(this)
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        createNotificationChannel(NotificationChannelType.REMINDER.channelId, "Reminders")
    }

    private fun createNotificationChannel(channelID: String, channelName: String) {
        val vibrationPattern = longArrayOf(500, 500, 500)
        val channel =
            NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
                description = "Example notification"
                setVibrationPattern(vibrationPattern)
                enableVibration(true)
                enableLights(true)
            }
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}