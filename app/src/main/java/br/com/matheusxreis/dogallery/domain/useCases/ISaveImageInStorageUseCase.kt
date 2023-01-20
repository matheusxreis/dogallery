package br.com.matheusxreis.dogallery.domain.useCases

interface ISaveImageInStorageUseCase {
    suspend fun execute(url:String):Boolean
}