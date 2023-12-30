import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.southernsunrise.notesapp.R
import com.southernsunrise.notesapp.ui.activities.NotesMainActivity
import com.southernsunrise.notesapp.notifications.NotificationChannelType

interface INotificationBuilder {
    fun showNotification()
}

class NotificationFactory {
    fun createNotification(
        context: Context,
        type: NotificationChannelType,
        title: String,
        description: String
    ): INotificationBuilder {
        return when (type) {
            NotificationChannelType.REMINDER -> ReminderNotification(context, title, description)
        }
    }
}

private class ReminderNotification(
    private val context: Context,
    private val title: String,
    private val description: String
) : INotificationBuilder {
    override fun showNotification() {
        showNotification(
            context,
            com.southernsunrise.notesapp.ui.activities.NotesMainActivity::class.java,
            title,
            description,
            NotificationChannelType.REMINDER.channelId
        )
    }
}


private fun showNotification(
    context: Context,
    pendingActivity: Class<*>,
    title: String,
    description: String,
    channelID: String
) {
    val intent = Intent(context, pendingActivity)
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val builder = NotificationCompat.Builder(context, channelID)
        .setContentTitle(title)
        .setContentText(description)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setSmallIcon(R.mipmap.ic_launcher_round)
        .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
        .setContentIntent(pendingIntent)
        .build()
    notificationManager.notify(System.currentTimeMillis().toInt(), builder)
}
