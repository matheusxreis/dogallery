package br.com.matheusxreis.dogimages.domain.useCases.implementations

import br.com.matheusxreis.dogimages.domain.irepositories.IGetRandomImageRepository
import br.com.matheusxreis.dogimages.domain.useCases.IGetRandomImageUseCase

class GetRandomImageUseCase constructor(private val repository: IGetRandomImageRepository):
    IGetRandomImageUseCase {

    override suspend fun execute():String{
        val result = repository.getRandomImageRepository()
        if(result.url.isEmpty()){
            throw Exception()
        }
        return result.url
    }

}