package com.shakib.baseapplication.common.di

import com.shakib.baseapplication.common.extensions.printInfoLog
import com.shakib.baseapplication.data.network.StackoverflowApi
import com.shakib.baseapplication.data.network.interceptor.AnalyticsInterceptor
import com.shakib.baseapplication.data.network.interceptor.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT_DURATION = 3L
    private const val baseUrl = "https://api.stackexchange.com/2.2/"

    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                printInfoLog(message)
            }
        })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun provideOkHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor,
        analyticsInterceptor: AnalyticsInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .readTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(analyticsInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()


    @RetrofitForRx
    @Provides
    fun provideRetrofitForRx(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(okHttpClient)
            .build()


    @RetrofitForFlow
    @Provides
    fun provideRetrofitForFlow(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()


    @ApiForRx
    @Provides
    fun provideRxApi(@RetrofitForRx retrofit: Retrofit): StackoverflowApi =
        retrofit.create(StackoverflowApi::class.java)

    @ApiForFlow
    @Provides
    fun provideFlowApi(@RetrofitForFlow retrofit: Retrofit): StackoverflowApi =
        retrofit.create(StackoverflowApi::class.java)
}