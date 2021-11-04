package com.shakib.baseapplication.common.di

import android.content.Context
import androidx.work.*
import com.shakib.baseapplication.presentation.screens.workmanager.ImageDownloadWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object WorkManagerModule {

    @Provides
    fun provideWorkManager(@ApplicationContext context: Context) = WorkManager.getInstance(context)

    @Provides
    fun provideConstraints() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresStorageNotLow(true)
        .setRequiresBatteryNotLow(true)
        .build()

    @OneTimeRequest
    @Provides
    fun provideOneTimeWorker(constraints: Constraints) =
        OneTimeWorkRequestBuilder<ImageDownloadWorker>()
            .setConstraints(constraints)
            .addTag("imageWork")
            .build()

    @DelayedRequest
    @Provides
    fun provideDelayedWorker(constraints: Constraints) =
        OneTimeWorkRequestBuilder<ImageDownloadWorker>()
            .setConstraints(constraints)
            .setInitialDelay(2, TimeUnit.SECONDS)
            .addTag("imageWork")
            .build()

    @Provides
    fun providePeriodicWorker(constraints: Constraints) =
        PeriodicWorkRequestBuilder<ImageDownloadWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .addTag("imageWork")
            .build()

    @Provides
    fun provideWorkQuery() = WorkQuery.Builder
        .fromTags(listOf("imageWork"))
        .addStates(listOf(WorkInfo.State.SUCCEEDED))
        .addUniqueWorkNames(
            listOf(
                "oneTimeImageDownload", "delayedImageDownload",
                "periodicImageDownload"
            )
        )
        .build()
}