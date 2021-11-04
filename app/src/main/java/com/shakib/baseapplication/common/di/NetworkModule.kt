package com.shakib.baseapplication.common.di

import com.shakib.baseapplication.common.extensions.printInfoLog
import com.shakib.baseapplication.data.network.GameApi
import com.shakib.baseapplication.data.network.StackOverflowApi
import com.shakib.baseapplication.data.network.interceptor.AnalyticsInterceptor
import com.shakib.baseapplication.data.network.interceptor.ApiKeyInterceptor
import com.shakib.baseapplication.data.network.interceptor.GameApiKeyInterceptor
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
    private const val gameUrl = "https://api.rawg.io/api/"

    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message -> printInfoLog(message) }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @GameHttp
    @Provides
    fun provideGameHttpClient(
        gameApiKeyInterceptor: GameApiKeyInterceptor,
        analyticsInterceptor: AnalyticsInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .readTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
        .addInterceptor(gameApiKeyInterceptor)
        .addInterceptor(analyticsInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    @OkHttp
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
    fun provideRetrofitForRx(@OkHttp okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(okHttpClient)
            .build()

    @RetrofitForFlow
    @Provides
    fun provideRetrofitForFlow(@OkHttp okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @RetrofitForGame
    @Provides
    fun provideRetrofitForGame(@GameHttp okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(gameUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @ApiForRx
    @Provides
    fun provideRxApi(@RetrofitForRx retrofit: Retrofit): StackOverflowApi =
        retrofit.create(StackOverflowApi::class.java)

    @ApiForFlow
    @Provides
    fun provideFlowApi(@RetrofitForFlow retrofit: Retrofit): StackOverflowApi =
        retrofit.create(StackOverflowApi::class.java)

    @Provides
    fun provideGameApi(@RetrofitForGame retrofit: Retrofit): GameApi =
        retrofit.create(GameApi::class.java)
}