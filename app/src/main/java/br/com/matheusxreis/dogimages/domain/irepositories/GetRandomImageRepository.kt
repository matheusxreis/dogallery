package br.com.matheusxreis.dogimages.domain.irepositories

import br.com.matheusxreis.dogimages.domain.entities.ImageData

interface GetRandomImageRepository {
    suspend fun getRandomImageRepository():ImageData
}