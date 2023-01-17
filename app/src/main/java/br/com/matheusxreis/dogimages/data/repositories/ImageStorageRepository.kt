package br.com.matheusxreis.dogimages.data.repositories

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import br.com.matheusxreis.dogimages.domain.entities.Image
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

    fun getImages(): List<Image> {

        val data = database.rawQuery("SELECT * FROM ${Constants.imagesTableName}", null)
        var list = listOf<Image>();

        while(data.moveToNext()){
            list = list.plus(Image(
                data.getString(0).toInt(),
                data.getString(1),
                data.getString(2).toLong()
            )
            )
        }
        return list;
    }

}