package br.com.matheusxreis.dogimages.domain.useCases.implementations

import br.com.matheusxreis.dogimages.domain.entities.Image
import br.com.matheusxreis.dogimages.domain.irepositories.IGetImagesFromStorageRepository
import br.com.matheusxreis.dogimages.domain.useCases.IGetImagesFromStorageUseCase

class GetImagesFromStorageUseCase constructor(private val repository: IGetImagesFromStorageRepository):IGetImagesFromStorageUseCase {
    override suspend fun execute(): List<Image> {
        return repository.getImages()
    }
}