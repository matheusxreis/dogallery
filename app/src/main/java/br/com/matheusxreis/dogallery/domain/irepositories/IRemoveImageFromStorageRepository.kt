package br.com.matheusxreis.dogallery.domain.irepositories

interface IRemoveImageFromStorageRepository {
    suspend fun remove(id:Int):Unit
}