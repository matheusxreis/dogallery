package br.com.matheusxreis.dogimages.domain.irepositories

import br.com.matheusxreis.dogimages.domain.entities.Image

interface IFindByUrlRepository {
    suspend fun findByUrl(url:String): Image?
}