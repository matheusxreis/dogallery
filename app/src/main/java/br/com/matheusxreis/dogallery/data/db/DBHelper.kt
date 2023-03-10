package br.com.matheusxreis.dogallery.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.matheusxreis.dogallery.utils.Constants

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(
            "CREATE TABLE ${Constants.imagesTableName} (" +
                    "${Constants.imagesColumnId} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "${Constants.imagesColumnUrl} TEXT," +
                    "${Constants.imagesColumnSavedAt} LONG)"
        )

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "DogImages.db"
    }
}