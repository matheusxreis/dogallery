package br.com.matheusxreis.dogimages.domain.useCases

interface IRemoveImageFromStorageUseCase {
    suspend fun execute(id:String):Boolean
}