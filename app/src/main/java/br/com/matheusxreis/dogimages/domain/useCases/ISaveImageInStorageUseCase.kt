package br.com.matheusxreis.dogimages.domain.useCases

interface ISaveImageInStorageUseCase {
    suspend fun execute(url:String):Boolean
}