package com.shakib.baseapplication.presentation.screens.workmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.shakib.baseapplication.R
import com.shakib.baseapplication.common.extensions.downloadVideo
import com.shakib.baseapplication.common.extensions.getUriFromUrl
import kotlinx.coroutines.delay

class VideoDownloadWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        // 1
        setForeground(createForegroundInfo())
        delay(3000)
        // 2
        val data = context.downloadVideo("https://cdn.videvo.net/videvo_files/video/free/2021-04/large_watermarked/210329_06B_Bali_1080p_013_preview.mp4")
        // 3
        return Result.success(workDataOf("DATA" to data))
    }

    private fun createForegroundInfo(): ForegroundInfo {
        // 1
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)
        // 2
        val notification = NotificationCompat.Builder(
            applicationContext, "workDownload"
        )
            .setContentTitle("Downloading Your Image")
            .setTicker("Downloading Your Image")
            .setSmallIcon(R.drawable.notification_action_background)
            .setOngoing(true)
            .addAction(android.R.drawable.ic_delete, "Cancel Download", intent)
        // 3
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(notification, "workDownload")
        }
        return ForegroundInfo(1, notification.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(
        notificationBuilder: NotificationCompat.Builder,
        id: String
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as
                    NotificationManager
        notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE)
        val channel = NotificationChannel(
            id,
            "WorkManagerApp",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "WorkManagerApp Notifications"
        notificationManager.createNotificationChannel(channel)
    }
}