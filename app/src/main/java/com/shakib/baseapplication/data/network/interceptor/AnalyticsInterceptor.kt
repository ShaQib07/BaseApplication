package com.shakib.baseapplication.data.network.interceptor

import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AnalyticsInterceptor @Inject constructor(@ApplicationContext private val context: Context): Interceptor {

    private val appVersion = "X-App-Version"
    private val osVersion = "X-Device-OS-Version"
    private val deviceModel = "X-Device-Model"
    private val devicePlatform = "X-Device-Platform"
    private val devicePlatformValue = "android"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val version = packageInfo.versionName

        requestBuilder
            .addHeader(appVersion, version)
            .addHeader(osVersion, Build.VERSION.SDK_INT.toString())
            .addHeader(deviceModel, Build.MODEL)
            .addHeader(devicePlatform, devicePlatformValue)

        return chain.proceed(requestBuilder.build())
    }
}