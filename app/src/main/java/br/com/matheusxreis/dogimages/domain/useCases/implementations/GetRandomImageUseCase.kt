package br.com.matheusxreis.dogimages.domain.useCases.implementations

import br.com.matheusxreis.dogimages.domain.irepositories.IGetRandomImageRepository
import br.com.matheusxreis.dogimages.domain.useCases.IGetRandomImageUseCase
import retrofit2.HttpException

class GetRandomImageUseCase constructor(private val repository: IGetRandomImageRepository):
    IGetRandomImageUseCase {

    override suspend fun execute():String {
        try {
            val url = repository.getRandomImageRepository().body()?.url as String
            if (url.isEmpty()) {
                throw Exception()
            }
            return url as String
        }catch (err: HttpException) {
            err.code()
            throw Exception()

        }
    }

}