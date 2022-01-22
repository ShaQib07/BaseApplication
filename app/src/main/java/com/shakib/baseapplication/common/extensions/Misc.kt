package com.shakib.baseapplication.common.extensions

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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
import java.io.*
import java.net.URL
import java.util.*

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

fun URL.toBitmap(): Bitmap? {
    return try {
        BitmapFactory.decodeStream(openStream())
    } catch (e: IOException) {
        null
    }
}

fun Bitmap.saveToInternalStorage(context: Context): Uri? {
    val wrapper = ContextWrapper(context)
    var file = wrapper.getDir("images", Context.MODE_PRIVATE)
    file = File(file, "${UUID.randomUUID()}.jpg")
    return try {
        val stream: OutputStream = FileOutputStream(file)
        compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.flush()
        stream.close()
        Uri.parse(file.absolutePath)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun Context.getUriFromUrl(): Uri? {
    // 1
    val imageUrl =
        URL(
            "https://images.pexels.com/photos/169573/pexels-photo-169573.jpeg" +
                    "?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"
        )
    // 2
    val bitmap = imageUrl.toBitmap()

    // 3
    var savedUri: Uri? = null
    bitmap?.apply {
        savedUri = saveToInternalStorage(this@getUriFromUrl)
    }
    return savedUri
}