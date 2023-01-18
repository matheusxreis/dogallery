package br.com.matheusxreis.dogimages.domain.irepositories

import br.com.matheusxreis.dogimages.domain.entities.Image

interface ISaveImageInStorageRepository {
    suspend fun saveImage(url:String):Unit
    suspend fun findByUrl(url:String): Image?
}