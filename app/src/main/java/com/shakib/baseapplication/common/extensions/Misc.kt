package com.shakib.baseapplication.common.extensions

import android.content.Context
import android.util.Log
import android.widget.Toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

inline fun <reified T> Any?.cast(): T? = if (this is T) this else null

fun printErrorLog(message: String) = Log.e("GSK", message)
fun printDebugLog(message: String) = Log.d("GSK", message)
fun printInfoLog(message: String) = Log.i("GSK", message)

fun Context.showShortToast(message: String, autoLog: Boolean = true) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    if (autoLog) printInfoLog("AutoLog - $message")
}

fun Context.showLongToast(message: String, autoLog: Boolean = true) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    if (autoLog) printInfoLog("AutoLog - $message")
}

fun <T : Any> Single<T>.execute(@NonNull observer: DisposableSingleObserver<T>): DisposableSingleObserver<T> =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(observer)


