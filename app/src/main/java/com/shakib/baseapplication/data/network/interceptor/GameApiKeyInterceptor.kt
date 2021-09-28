package com.shakib.baseapplication.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class GameApiKeyInterceptor @Inject constructor() : Interceptor {

    private val apiKey = "key"
    private val gameApiKey = "4490a60286684172b7bdb47257b388db"

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