package br.com.matheusxreis.dogallery.domain.useCases

interface IAlreadyExistUseCase {
   suspend fun execute(url:String):Boolean
}