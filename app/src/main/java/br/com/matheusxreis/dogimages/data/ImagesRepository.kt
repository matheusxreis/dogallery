package br.com.matheusxreis.dogimages.data

import android.util.Log
import androidx.compose.ui.unit.Constraints
import br.com.matheusxreis.dogimages.domain.entities.ImageData
import br.com.matheusxreis.dogimages.domain.irepositories.IGetRandomImageRepository
import br.com.matheusxreis.dogimages.utils.Api
import br.com.matheusxreis.dogimages.utils.Constants
import retrofit2.Response

class ImagesRepository: IGetRandomImageRepository {

    lateinit private var api: IGetRandomImageRepository;

    init {
        api = Api.getApi(Constants.defaultBaseUrl).create(IGetRandomImageRepository::class.java)
    }
    override suspend fun getRandomImageRepository(): Response<ImageData> {
        val result = api.getRandomImageRepository()


        return result;
    }
}