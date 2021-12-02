package com.shakib.baseapplication.presentation.screens.workmanager

import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.shakib.baseapplication.common.base.BaseViewModel
import com.shakib.baseapplication.common.di.DelayedRequest
import com.shakib.baseapplication.common.di.OneTimeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WorkManagerViewModel @Inject constructor(
    val workManager: WorkManager,
    @OneTimeRequest private val oneTimeWorkRequest: OneTimeWorkRequest,
    @DelayedRequest private val delayedWorkRequest: OneTimeWorkRequest,
    private val periodicWorkRequest: PeriodicWorkRequest,
    val workQuery: WorkQuery
) : BaseViewModel() {
    val requestIdLiveData = MutableLiveData<UUID>()

    fun createOneTimeWorkRequest() {
        workManager.enqueueUniqueWork(
            "oneTimeImageDownload",
            ExistingWorkPolicy.KEEP,
            oneTimeWorkRequest
        )
        requestIdLiveData.value = oneTimeWorkRequest.id
    }

    fun createDelayedWorkRequest() {
        workManager.enqueueUniqueWork(
            "delayedImageDownload",
            ExistingWorkPolicy.KEEP,
            delayedWorkRequest
        )
        requestIdLiveData.value = delayedWorkRequest.id
    }

    fun createPeriodicWorkRequest() {
        workManager.enqueueUniquePeriodicWork(
            "periodicImageDownload",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
        requestIdLiveData.value = periodicWorkRequest.id
    }

    fun createChainWorkRequest() {
        workManager.beginWith(oneTimeWorkRequest).then(delayedWorkRequest).enqueue()
        requestIdLiveData.value = delayedWorkRequest.id
    }

    override fun onClear() {}
}