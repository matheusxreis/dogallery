package br.com.matheusxreis.dogimages.domain.useCases

import android.content.Context

interface IDownloadImageUseCase {
    suspend fun execute(url:String, nameFile:String, context: Context):Boolean
}