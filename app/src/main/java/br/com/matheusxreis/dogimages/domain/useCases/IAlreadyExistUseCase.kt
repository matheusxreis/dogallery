package br.com.matheusxreis.dogimages.domain.useCases

interface IAlreadyExistUseCase {
   suspend fun execute(url:String):Boolean
}