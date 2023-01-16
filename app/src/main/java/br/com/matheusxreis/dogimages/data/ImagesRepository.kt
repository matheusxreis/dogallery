package br.com.matheusxreis.dogimages.data

import android.util.Log
import br.com.matheusxreis.dogimages.domain.entities.ImageData
import br.com.matheusxreis.dogimages.domain.irepositories.IGetRandomImageRepository

class ImagesRepository: IGetRandomImageRepository {
    override suspend fun getRandomImageRepository(): ImageData {
        val result = ImageData("url")
        Log.d("im here", "")
        return result;
    }
}