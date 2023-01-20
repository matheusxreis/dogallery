package br.com.matheusxreis.dogallery.domain.useCases.implementations

import br.com.matheusxreis.dogallery.domain.entities.Image
import br.com.matheusxreis.dogallery.domain.irepositories.IGetImagesFromStorageRepository
import br.com.matheusxreis.dogallery.domain.useCases.IGetImagesFromStorageUseCase

class GetImagesFromStorageUseCase constructor(private val repository: IGetImagesFromStorageRepository):IGetImagesFromStorageUseCase {
    override suspend fun execute(): List<Image> {
        return repository.getImages()
    }
}