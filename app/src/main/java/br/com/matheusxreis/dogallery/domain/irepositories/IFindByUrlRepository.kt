package br.com.matheusxreis.dogallery.domain.irepositories

import br.com.matheusxreis.dogallery.domain.entities.Image

interface IFindByUrlRepository {
    suspend fun findByUrl(url:String): Image?
}