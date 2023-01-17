package br.com.matheusxreis.dogimages.domain.useCases.implementations

import br.com.matheusxreis.dogimages.domain.irepositories.ISaveImageInStorageRepository
import br.com.matheusxreis.dogimages.domain.useCases.ISaveImageInStorageUseCase

class SaveImageInStorageUseCase constructor(private val repository: ISaveImageInStorageRepository):ISaveImageInStorageUseCase {
    override suspend fun execute(url: String): Boolean {
       try{
           repository.saveImage(url)
           return true;
       }catch (err:Exception){
           return false;
       }
    }
}