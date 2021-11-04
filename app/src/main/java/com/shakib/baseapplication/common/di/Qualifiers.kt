package com.shakib.baseapplication.common.di

import javax.inject.Qualifier

/*-------------- okhttp qualifier --------------*/
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkHttp

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GameHttp


/*-------------- retrofit qualifier --------------*/
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitForRx

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitForFlow

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitForGame


/*-------------- api qualifier --------------*/
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiForRx

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiForFlow


/*-------------- work request qualifier --------------*/
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OneTimeRequest

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DelayedRequest