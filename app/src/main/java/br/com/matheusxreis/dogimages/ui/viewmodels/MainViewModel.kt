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
import br.com.matheusxreis.dogimages.domain.useCases.IAlreadyExistUseCase
import br.com.matheusxreis.dogimages.domain.useCases.IGetImagesFromStorageUseCase
import br.com.matheusxreis.dogimages.domain.useCases.IGetRandomImageUseCase
import br.com.matheusxreis.dogimages.domain.useCases.ISaveImageInStorageUseCase
import br.com.matheusxreis.dogimages.domain.useCases.implementations.AlreadyExistUseCase
import br.com.matheusxreis.dogimages.domain.useCases.implementations.GetImagesFromStorageUseCase
import br.com.matheusxreis.dogimages.domain.useCases.implementations.GetRandomImageUseCase
import br.com.matheusxreis.dogimages.domain.useCases.implementations.SaveImageInStorageUseCase
import br.com.matheusxreis.dogimages.helpers.NetworkHelper
import kotlinx.coroutines.launch

class MainViewModel(application: Application):AndroidViewModel(application) {

    lateinit private var getRandomImageUseCase: IGetRandomImageUseCase;
    lateinit private var saveImageInStorageUseCase: ISaveImageInStorageUseCase;
    lateinit private var getImageInStorageUseCase: IGetImagesFromStorageUseCase;
    lateinit private var alreadyExistUseCase: IAlreadyExistUseCase;


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

    var amountImagesSaved by mutableStateOf(0)
        private set;

    init {
        getRandomImageUseCase = GetRandomImageUseCase(ImagesRepository())
        var storageRepo = ImageStorageRepository(application)
        saveImageInStorageUseCase = SaveImageInStorageUseCase(storageRepo)
        getImageInStorageUseCase = GetImagesFromStorageUseCase(storageRepo)
        alreadyExistUseCase = AlreadyExistUseCase(storageRepo)

        getAmountSaved()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getRandomImage(){
        isLoading = true;
        val conn = NetworkHelper.isConnected(context = getApplication<Application>().applicationContext)
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

    fun getAmountSaved(){
        viewModelScope.launch {
            val list = getImageInStorageUseCase.execute()
            amountImagesSaved = list.size

        }
    }

    fun saveImageInStorage(url:String, callback: ()->Unit){
         viewModelScope.launch {
            try {
                imageWasSaved = true
                var result = saveImageInStorageUseCase.execute(url);
                if(result){
                    callback()
                }

                getAmountSaved()

            }catch(err:Exception){
                imageWasSaved = false;
            }
        }
    }

    fun alreadyIsSaved(url:String):Boolean {
        var already:Boolean? = null;
        viewModelScope.launch {
            already = alreadyExistUseCase.execute(url)
        }
        return already as Boolean;
    }
}