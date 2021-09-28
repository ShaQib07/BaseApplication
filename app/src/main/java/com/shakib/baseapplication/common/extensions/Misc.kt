package com.shakib.baseapplication.common.extensions

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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

fun ImageView.loadImageFromUrl(url: String) =
    Glide
        .with(this.context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .into(this)

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
