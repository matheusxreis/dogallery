package br.com.matheusxreis.dogallery.domain.useCases

import br.com.matheusxreis.dogallery.domain.entities.Image

interface IGetImagesFromStorageUseCase {
    suspend fun execute():List<Image>
}