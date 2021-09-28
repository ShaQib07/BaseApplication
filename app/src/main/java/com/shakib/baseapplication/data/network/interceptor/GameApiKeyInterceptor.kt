package com.shakib.baseapplication.data.network.interceptor

import android.content.Context
import com.shakib.baseapplication.R
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class GameApiKeyInterceptor @Inject constructor(@ApplicationContext context: Context) : Interceptor {

    private val apiKey = context.getString(R.string.api_key)
    private val gameApiKey = context.getString(R.string.game_api_key)

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(apiKey, gameApiKey)
            .build()

        val newRequest = originalRequest.newBuilder().url(newUrl).build()

        return chain.proceed(newRequest)
    }
}