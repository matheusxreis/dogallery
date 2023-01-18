package br.com.matheusxreis.dogimages.domain.useCases.implementations

import br.com.matheusxreis.dogimages.domain.irepositories.IRemoveImageFromStorageRepository
import br.com.matheusxreis.dogimages.domain.useCases.IRemoveImageFromStorageUseCase

class RemoveImageFromStorageUseCase constructor(private val repository:IRemoveImageFromStorageRepository):IRemoveImageFromStorageUseCase{
    override suspend fun execute(id: String): Boolean {
        return try{
            repository.remove(id)
            true
        }catch(err:Exception){
            false
        }
    }
}