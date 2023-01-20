package br.com.matheusxreis.dogallery.domain.useCases

interface IRemoveImageFromStorageUseCase {
    suspend fun execute(id:Int):Boolean
}