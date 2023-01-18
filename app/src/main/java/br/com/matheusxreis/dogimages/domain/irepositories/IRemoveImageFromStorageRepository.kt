package br.com.matheusxreis.dogimages.domain.irepositories

interface IRemoveImageFromStorageRepository {
    suspend fun remove(id:String):Unit
}