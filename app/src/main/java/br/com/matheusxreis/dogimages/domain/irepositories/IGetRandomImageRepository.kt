package br.com.matheusxreis.dogimages.domain.irepositories

import br.com.matheusxreis.dogimages.domain.entities.ImageData
import retrofit2.Response
import retrofit2.http.GET

interface IGetRandomImageRepository {
    @GET("breeds/image/random")
    suspend fun getRandomImageRepository():Response<ImageData>
}