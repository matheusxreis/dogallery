package br.com.matheusxreis.dogimages.data.repositories

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import br.com.matheusxreis.dogimages.utils.Constants
import br.com.matheusxreis.dogimages.utils.DBHelper

class ImageStorageRepository constructor(context:Context){

    lateinit var  helper: SQLiteOpenHelper;
    lateinit var database: SQLiteDatabase;

    init {
        helper = DBHelper(context, null)
        database = helper.writableDatabase
    }

    fun saveImage(url:String){
        Log.d("SAVEEEEE", url)
        database.insert(
            Constants.imagesTableName,
            null,
            ContentValues().apply {
                put(Constants.imagesColumnId, (1..1000).random())
                put(Constants.imagesColumnUrl, url)
                put(Constants.imagesColumnSavedAt, 10920182818291892)
            }

        )
    }

    @SuppressLint("Range")
    fun getImages(){

        val data = database.rawQuery("SELECT * FROM ${Constants.imagesTableName}", null)
        Log.d("SAVEE2", data.toString())
        data.getString(data.getColumnIndex(Constants.imagesColumnUrl))
    }

}