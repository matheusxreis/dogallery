package br.com.matheusxreis.dogimages.domain.useCases.implementations

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import br.com.matheusxreis.dogimages.domain.useCases.IDownloadImageUseCase
import java.io.*
import java.net.URL

class DownloadImageUseCase:IDownloadImageUseCase {

    private fun transformBitmap(urlImage: String): Bitmap {
        val url: URL = URL(urlImage)
        val inputStream: InputStream = url.openStream();
        return BitmapFactory.decodeStream(inputStream);
    }

    private fun galleryAddPic(currentPhotoPath:String, context: Context){
        Intent(
            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
                val f = File(currentPhotoPath)
                mediaScanIntent.data = Uri.fromFile(f)
                context.sendBroadcast(mediaScanIntent)
             }
    }

    override suspend fun execute(url: String, nameFile: String, context: Context): Boolean {
        val bitmap = transformBitmap(url)
        val path: File = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "DogGallery")
        val file: File = File(path,  "$nameFile.png")
        return try{
            path.mkdirs();
            val outputStream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            galleryAddPic(file.toString(), context);
            true;
        }catch(e: IOException){
            Log.w("ExternalStorageMatheus", "Error writing $file", e);
            false
        }
    }

}