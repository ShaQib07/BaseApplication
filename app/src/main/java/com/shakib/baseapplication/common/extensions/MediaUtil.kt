package com.shakib.baseapplication.common.extensions

import android.content.Context
import com.shakib.baseapplication.R
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.net.URLConnection

fun Context.downloadVideo(downloadUrl: String): String {
    var message: String
    val outputFile = File(getOutputDirectory(this), "VIDEO_001.mp4")
    try {
        printDebugLog("File download Started")
        val downloadingUrl = URL(downloadUrl)
        val urlConnection: URLConnection = URL(downloadUrl).openConnection()
        val contentLength: Int = urlConnection.contentLength
        val stream = DataInputStream(downloadingUrl.openStream())
        val buffer = ByteArray(contentLength)
        stream.readFully(buffer)
        stream.close()
        val fos = DataOutputStream(FileOutputStream(outputFile))
        fos.apply {
            write(buffer)
            flush()
            close()
        }
        val size = outputFile.length().div(1024 * 1024)
        printDebugLog("File download complete - $size MB")
        message = outputFile.absolutePath
    } catch (e: Exception) {
        printDebugLog("File download failed $e")
        message = "File download failed $e"
    }
    return message
}

private fun getOutputDirectory(context: Context): File {
    val mediaDir = context.externalCacheDirs.firstOrNull()?.let {
        File(it, context.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
}