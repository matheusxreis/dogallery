package br.com.matheusxreis.dogimages.domain.useCases.implementations

import br.com.matheusxreis.dogimages.domain.errors.BadRequest
import br.com.matheusxreis.dogimages.domain.errors.NotFound
import br.com.matheusxreis.dogimages.domain.irepositories.IGetRandomImageRepository
import br.com.matheusxreis.dogimages.domain.useCases.IGetRandomImageUseCase
import retrofit2.HttpException

class GetRandomImageUseCase constructor(private val repository: IGetRandomImageRepository):
    IGetRandomImageUseCase {

    override suspend fun execute():String {
        try {
            val response = repository.getRandomImageRepository()

            when(response.code()) {
                400 -> throw BadRequest()
                404 -> throw NotFound()
            }

           val url = response.body()?.url as String
            if (url.isEmpty()) {
                throw Exception()
            }
            return url as String
        }catch (err: HttpException) {
            
            when(err.code()) {
                400 -> throw BadRequest()
                404 -> throw NotFound()
                else -> throw Exception()
            }



        }
    }

}