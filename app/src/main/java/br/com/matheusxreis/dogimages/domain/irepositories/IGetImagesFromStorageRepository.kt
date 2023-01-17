package br.com.matheusxreis.dogimages.domain.irepositories

import br.com.matheusxreis.dogimages.domain.entities.Image

interface IGetImagesFromStorageRepository {
    suspend fun getImages():List<Image>
}