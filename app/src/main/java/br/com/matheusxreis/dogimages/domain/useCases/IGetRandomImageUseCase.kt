package br.com.matheusxreis.dogimages.domain.useCases

interface IGetRandomImageUseCase {
    suspend fun execute():String
}