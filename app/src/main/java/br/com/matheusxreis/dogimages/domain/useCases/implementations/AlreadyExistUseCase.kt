package br.com.matheusxreis.dogimages.domain.useCases.implementations

import br.com.matheusxreis.dogimages.domain.irepositories.IFindByUrlRepository
import br.com.matheusxreis.dogimages.domain.useCases.IAlreadyExistUseCase

class AlreadyExistUseCase constructor(private val repository: IFindByUrlRepository):IAlreadyExistUseCase{
    override suspend fun execute(url: String): Boolean {
        val result = repository.findByUrl(url)
        if(result != null){ return true }
        return false
    }
}