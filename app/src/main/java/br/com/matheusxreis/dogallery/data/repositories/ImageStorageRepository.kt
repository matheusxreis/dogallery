package br.com.matheusxreis.dogallery.data.repositories

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import br.com.matheusxreis.dogallery.domain.entities.Image
import br.com.matheusxreis.dogallery.domain.irepositories.IFindByUrlRepository
import br.com.matheusxreis.dogallery.domain.irepositories.IGetImagesFromStorageRepository
import br.com.matheusxreis.dogallery.domain.irepositories.IRemoveImageFromStorageRepository
import br.com.matheusxreis.dogallery.domain.irepositories.ISaveImageInStorageRepository
import br.com.matheusxreis.dogallery.utils.Constants
import br.com.matheusxreis.dogallery.data.db.DBHelper

class ImageStorageRepository constructor(context:Context):
    ISaveImageInStorageRepository,
    IGetImagesFromStorageRepository,
    IRemoveImageFromStorageRepository,
    IFindByUrlRepository
{

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

    override suspend fun remove(id: Int) {
        Log.d("REMOVE", id.toString())
       database.delete(
           Constants.imagesTableName,
           "${Constants.imagesColumnId} = $id",
           null
       )
    }

    override suspend fun findByUrl(url:String):Image?{
        val data = database.rawQuery("SELECT * FROM ${Constants.imagesTableName} WHERE ${Constants.imagesColumnUrl}="+"'$url'", null)
        var exist:Image? = null;
        while(data.moveToNext()){
           if(data.getString(1).isNotEmpty()){
               exist = Image(
                   id = data.getString(0).toInt(),
                   url = data.getString(1),
                   savedAt = data.getString(2).toLong()
               )
           }
        }
        return exist
    }

}