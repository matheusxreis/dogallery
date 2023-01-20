package br.com.matheusxreis.dogallery.domain.irepositories

import br.com.matheusxreis.dogallery.domain.entities.Image

interface IGetImagesFromStorageRepository {
    suspend fun getImages():List<Image>
}