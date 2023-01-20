package br.com.matheusxreis.dogallery.domain.irepositories

import br.com.matheusxreis.dogallery.domain.entities.Image

interface ISaveImageInStorageRepository {
    suspend fun saveImage(url:String):Unit
    suspend fun findByUrl(url:String): Image?
}