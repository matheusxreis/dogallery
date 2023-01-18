package br.com.matheusxreis.dogimages.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import android.widget.Toast
import br.com.matheusxreis.dogimages.MainActivity
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class Downloader {

    private val executor = Executors.newSingleThreadExecutor();

    fun transformBitmap(urlImage: String): Bitmap {

        Log.d("DEU CERTO", "UHU")

        val url: URL = URL(urlImage)
        val inputStream: InputStream = url.openStream();
        return BitmapFactory.decodeStream(inputStream);

    }

    fun downloadImage(nameFile: String, bitmap: Bitmap){
        val filePath:File = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), nameFile)
        val outputStream: OutputStream = FileOutputStream(filePath)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        Log.d("DEU CERTO", "UHU")
    }
}

