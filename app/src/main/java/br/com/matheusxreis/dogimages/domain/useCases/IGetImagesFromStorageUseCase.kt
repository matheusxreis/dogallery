package br.com.matheusxreis.dogimages.domain.useCases

import br.com.matheusxreis.dogimages.domain.entities.Image

interface IGetImagesFromStorageUseCase {
    suspend fun execute():List<Image>
}