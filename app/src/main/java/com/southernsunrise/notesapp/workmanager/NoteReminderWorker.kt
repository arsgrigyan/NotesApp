package com.southernsunrise.notesapp.workmanager

import INotificationBuilder
import NotificationFactory
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.southernsunrise.notesapp.notifications.NotificationChannelType
import com.southernsunrise.notesapp.notifications.NotificationChannelType.*

class NoteReminderWorker(
    private val context: Context? = null,
    workerParams: WorkerParameters,
) :
    CoroutineWorker(context!!, workerParams) {
    override suspend fun doWork(): Result {
        showNotification(description = "Reminder for the note: ${inputData.getString("NOTE_TITLE")}")
        return Result.success()
    }

    private fun showNotification(
        type: NotificationChannelType = REMINDER,
        title: String = "Note Reminder!",
        description: String
    ) {
        val notificationFactory = NotificationFactory()
        val notification: INotificationBuilder =
            notificationFactory.createNotification(context!!, type, title, description)
        notification.showNotification()
    }
}