package br.com.matheusxreis.dogallery.domain.useCases.implementations

import br.com.matheusxreis.dogallery.domain.irepositories.IFindByUrlRepository
import br.com.matheusxreis.dogallery.domain.useCases.IAlreadyExistUseCase

class AlreadyExistUseCase constructor(private val repository: IFindByUrlRepository):IAlreadyExistUseCase{
    override suspend fun execute(url: String): Boolean {
        val result = repository.findByUrl(url)
        if(result != null){ return true }
        return false
    }
}