package br.com.matheusxreis.dogimages.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.matheusxreis.dogimages.data.repositories.ImageStorageRepository
import br.com.matheusxreis.dogimages.domain.entities.Image
import br.com.matheusxreis.dogimages.domain.useCases.IGetImagesFromStorageUseCase
import br.com.matheusxreis.dogimages.domain.useCases.implementations.GetImagesFromStorageUseCase
import kotlinx.coroutines.launch

class GalleryViewModel(application: Application): AndroidViewModel(application) {

    lateinit private var getImageInStorageUseCase: IGetImagesFromStorageUseCase;

    init {
        var storageRepo = ImageStorageRepository(application);
        getImageInStorageUseCase = GetImagesFromStorageUseCase(storageRepo);

    }

    var images by mutableStateOf<List<Image>>(listOf())
        private set;

    fun getImages(){
        viewModelScope.launch {
            val result = getImageInStorageUseCase.execute()
            images = result;
        }
    }

}