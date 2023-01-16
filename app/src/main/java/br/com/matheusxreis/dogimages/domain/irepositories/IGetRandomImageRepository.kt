package br.com.matheusxreis.dogimages.domain.irepositories

import br.com.matheusxreis.dogimages.domain.entities.ImageData

interface IGetRandomImageRepository {
    suspend fun getRandomImageRepository():ImageData
}