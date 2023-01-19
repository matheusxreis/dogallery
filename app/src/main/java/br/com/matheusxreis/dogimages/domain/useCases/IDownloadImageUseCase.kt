package br.com.matheusxreis.dogimages.domain.useCases

interface IDownloadImageUseCase {
    suspend fun execute(url:String, nameFile:String):Boolean
}