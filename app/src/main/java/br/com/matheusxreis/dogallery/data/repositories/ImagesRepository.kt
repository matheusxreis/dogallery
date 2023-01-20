package br.com.matheusxreis.dogallery.data.repositories
import br.com.matheusxreis.dogallery.domain.entities.ImageData
import br.com.matheusxreis.dogallery.domain.irepositories.IGetRandomImageRepository
import br.com.matheusxreis.dogallery.utils.Api
import br.com.matheusxreis.dogallery.utils.Constants
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