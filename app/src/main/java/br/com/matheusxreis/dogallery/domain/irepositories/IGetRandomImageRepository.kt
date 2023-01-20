package br.com.matheusxreis.dogallery.domain.irepositories

import br.com.matheusxreis.dogallery.domain.entities.ImageData
import retrofit2.Response
import retrofit2.http.GET

interface IGetRandomImageRepository {
    @GET("breeds/image/random")
    suspend fun getRandomImageRepository():Response<ImageData>
}