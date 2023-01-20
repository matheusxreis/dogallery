package br.com.matheusxreis.dogallery.domain.useCases.implementations

import br.com.matheusxreis.dogallery.domain.irepositories.IRemoveImageFromStorageRepository
import br.com.matheusxreis.dogallery.domain.useCases.IRemoveImageFromStorageUseCase

class RemoveImageFromStorageUseCase constructor(private val repository:IRemoveImageFromStorageRepository):IRemoveImageFromStorageUseCase{
    override suspend fun execute(id: Int): Boolean {
        return try{
            repository.remove(id)
            true
        }catch(err:Exception){
            false
        }
    }
}