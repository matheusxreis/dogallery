package br.com.matheusxreis.dogimages.ui.viewmodels

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.matheusxreis.dogimages.data.repositories.ImageStorageRepository
import br.com.matheusxreis.dogimages.domain.entities.Image
import br.com.matheusxreis.dogimages.domain.useCases.IDownloadImageUseCase
import br.com.matheusxreis.dogimages.domain.useCases.IGetImagesFromStorageUseCase
import br.com.matheusxreis.dogimages.domain.useCases.IRemoveImageFromStorageUseCase
import br.com.matheusxreis.dogimages.domain.useCases.implementations.DownloadImageUseCase
import br.com.matheusxreis.dogimages.domain.useCases.implementations.GetImagesFromStorageUseCase
import br.com.matheusxreis.dogimages.domain.useCases.implementations.RemoveImageFromStorageUseCase
import br.com.matheusxreis.dogimages.helpers.NotificationHelper
import br.com.matheusxreis.dogimages.helpers.NetworkHelper
import br.com.matheusxreis.dogimages.utils.RandomNameImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GalleryViewModel(application: Application): AndroidViewModel(application) {

    lateinit private var getImageInStorageUseCase: IGetImagesFromStorageUseCase;
    lateinit private var removeImageInStorageUseCase: IRemoveImageFromStorageUseCase;
    lateinit private var downloadImageUseCase: IDownloadImageUseCase


    init {
        var storageRepo = ImageStorageRepository(application);
        getImageInStorageUseCase = GetImagesFromStorageUseCase(storageRepo);
        removeImageInStorageUseCase = RemoveImageFromStorageUseCase(storageRepo);
        downloadImageUseCase = DownloadImageUseCase();

    }


    var images by mutableStateOf<List<Image>>(listOf())
        private set;
    var isConnected by mutableStateOf(true)
        private set;
    var downloading by mutableStateOf<Boolean?>(false)
        private set;


    @RequiresApi(Build.VERSION_CODES.M)
    fun getImages(){

        val conn = NetworkHelper.isConnected(context = getApplication<Application>().applicationContext)

        if(conn) {
            isConnected = true
            viewModelScope.launch {
                val result = getImageInStorageUseCase.execute()
                images = result;
            }
        }else {
            isConnected = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun removeImage(id: Int){
        viewModelScope.launch {
           removeImageInStorageUseCase.execute(id)
           getImages()
        }
    }


    fun downloadImage(url:String){
        downloading = true
        val nameFile = RandomNameImage.generate(url)

        viewModelScope.launch(Dispatchers.Default) {
            val result = downloadImageUseCase.execute(
                url,
                nameFile
            )
            if(!result){
                downloading = null
            }else {
                val notification = NotificationHelper(getApplication<Application>().applicationContext);
                val channelId = "DOWNLOAD_CHANNEL_ID"
                notification.createNotificationChannel(channelId);
                notification.dispareNotification(channelId, "Download Finished", "Download of the dog image: ${nameFile.slice(0..10)}... was finished.");
                downloading = false
            }

        }


    }

}