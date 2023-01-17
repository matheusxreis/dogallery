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
import br.com.matheusxreis.dogimages.data.repositories.ImagesRepository
import br.com.matheusxreis.dogimages.domain.useCases.IGetRandomImageUseCase
import br.com.matheusxreis.dogimages.domain.useCases.ISaveImageInStorageUseCase
import br.com.matheusxreis.dogimages.domain.useCases.implementations.GetRandomImageUseCase
import br.com.matheusxreis.dogimages.domain.useCases.implementations.SaveImageInStorageUseCase
import br.com.matheusxreis.dogimages.utils.Network
import kotlinx.coroutines.launch

class MainViewModel(application: Application):AndroidViewModel(application) {

    lateinit private var getRandomImageUseCase: IGetRandomImageUseCase;
    lateinit private var saveImageInStorageUseCase: ISaveImageInStorageUseCase;


    var actualImageUrl by mutableStateOf("")
        private set;
    var lastImageUrl by mutableStateOf("")
        private set;
    var isLoading by mutableStateOf(false)
        private set;
    var isConnected by mutableStateOf(true)
        private set;

    var imageWasSaved by mutableStateOf(true)
        private set;

    init {
        getRandomImageUseCase = GetRandomImageUseCase(ImagesRepository())

        saveImageInStorageUseCase = SaveImageInStorageUseCase(ImageStorageRepository(application))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getRandomImage(){
        isLoading = true;
        val conn = Network.isConnected(context = getApplication<Application>().applicationContext)
        if(!conn){
            isConnected = false;
            isLoading = false;
            return;
        }
        isConnected = true;
        viewModelScope.launch {
            var url = getRandomImageUseCase.execute()
            if(actualImageUrl.length>0) {
                lastImageUrl = actualImageUrl;
            }
             actualImageUrl = url;
            isLoading = false;
        }
    }

    fun comeBackImage(){
        var image = lastImageUrl;
        lastImageUrl = actualImageUrl;
        actualImageUrl = image;
    }

    fun saveImageInStorage(url:String){
        viewModelScope.launch {
            imageWasSaved = !imageWasSaved
            imageWasSaved = saveImageInStorageUseCase.execute(url)
        }
    }
}