package br.com.matheusxreis.dogimages.domain.useCases

interface IRemoveImageFromStorageUseCase {
    suspend fun execute(id:Int):Boolean
}