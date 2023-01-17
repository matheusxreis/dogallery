package br.com.matheusxreis.dogimages.domain.irepositories

interface ISaveImageInStorageRepository {
    suspend fun saveImage(url:String):Unit
}