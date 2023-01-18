package br.com.matheusxreis.dogimages.data.repositories

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import br.com.matheusxreis.dogimages.domain.entities.Image
import br.com.matheusxreis.dogimages.domain.irepositories.IGetImagesFromStorageRepository
import br.com.matheusxreis.dogimages.domain.irepositories.IRemoveImageFromStorageRepository
import br.com.matheusxreis.dogimages.domain.irepositories.ISaveImageInStorageRepository
import br.com.matheusxreis.dogimages.utils.Constants
import br.com.matheusxreis.dogimages.utils.DBHelper

class ImageStorageRepository constructor(context:Context):
    ISaveImageInStorageRepository,
    IGetImagesFromStorageRepository,
    IRemoveImageFromStorageRepository{

    lateinit var  helper: SQLiteOpenHelper;
    lateinit var database: SQLiteDatabase;

    init {
        helper = DBHelper(context, null)
        database = helper.writableDatabase
    }

    override suspend fun saveImage(url:String){
        Log.d("SAVEEEEE", url)
        database.insert(
            Constants.imagesTableName,
            null,
            ContentValues().apply {
                put(Constants.imagesColumnUrl, url)
                put(Constants.imagesColumnSavedAt, 10920182818291892)
            }

        )
    }

    override suspend fun getImages(): List<Image> {

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

    override suspend fun remove(id: String) {
       database.rawQuery(
           "DELETE FROM ${Constants.imagesTableName} WHERE ${Constants.imagesColumnId}=$id",
           null
       )
    }

}