package br.com.matheusxreis.dogimages.domain.useCases

import br.com.matheusxreis.dogimages.domain.irepositories.GetRandomImageRepository

class GetRandomImageUseCase constructor(private val repository: GetRandomImageRepository){

    suspend fun execute():String{
        val result = repository.getRandomImageRepository()
        return result.url
    }

}