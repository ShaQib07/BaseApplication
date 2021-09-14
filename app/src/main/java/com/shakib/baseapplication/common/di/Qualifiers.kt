package com.shakib.baseapplication.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitForRx

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitForFlow

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiForRx

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiForFlow