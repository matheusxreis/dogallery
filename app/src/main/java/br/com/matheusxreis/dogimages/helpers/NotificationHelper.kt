package br.com.matheusxreis.dogimages.helpers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import br.com.matheusxreis.dogimages.R

class NotificationHelper(private val context:Context) {
    fun createNotificationChannel(channelId:String){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "dog-gallery-channel"
            val descriptionText = "notification-from-gallery"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }

    }
    fun dispareNotification(
        channelId:String,
        title: String,
        content: String){
        val builder = NotificationCompat.Builder(
            context,
            channelId
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(12, builder.build())
        }
    }
}