package com.shakib.baseapplication.common.extensions

import android.content.Context
import android.util.Log
import android.widget.Toast

inline fun <reified T> Any?.cast(): T? = if (this is T) this else null

fun printErrorLog(message: String) = Log.e("GSK", message)
fun printDebugLog(message: String) = Log.d("GSK", message)
fun printInfoLog(message: String) = Log.i("GSK", message)

fun Context.showShortToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun Context.showLongToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
