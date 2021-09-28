package com.shakib.baseapplication.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkHttp

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GameHttp


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitForRx

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitForFlow

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitForGame


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiForRx

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiForFlow