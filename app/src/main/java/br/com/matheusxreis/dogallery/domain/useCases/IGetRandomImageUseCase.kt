package br.com.matheusxreis.dogallery.domain.useCases

interface IGetRandomImageUseCase {
    suspend fun execute():String
}