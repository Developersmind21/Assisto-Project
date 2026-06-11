package com.example.assisto.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.assisto.MainActivity
import com.example.assisto.R

object NotificationHelper {
    const val CHANNEL_ID = "assisto_requests"
    const val INCOMING_REQUEST_NOTIFICATION_ID = 1001
    const val EXTRA_OPEN_INCOMING_REQUEST = "open_incoming_request"

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Job Requests",
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description = "Incoming service request alerts for providers"
                enableVibration(true)
            }
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    fun showIncomingRequestNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(EXTRA_OPEN_INCOMING_REQUEST, true)
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("New Service Request")
            .setContentText("Plumbing job nearby — $75–$120. Tap to respond.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(context).notify(INCOMING_REQUEST_NOTIFICATION_ID, notification)
    }
}
