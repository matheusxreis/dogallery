package br.com.matheusxreis.dogimages.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.matheusxreis.dogimages.data.ImagesRepository
import br.com.matheusxreis.dogimages.domain.useCases.IGetRandomImageUseCase
import br.com.matheusxreis.dogimages.domain.useCases.implementations.GetRandomImageUseCase
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {

    lateinit private var getRandomImageUseCase: IGetRandomImageUseCase;


    var actualImageUrl by mutableStateOf("")
        private set;
    var lastImageUrl by mutableStateOf("")
        private set;
    var isLoading by mutableStateOf(false)
        private set;

    init {
        getRandomImageUseCase = GetRandomImageUseCase(ImagesRepository())
    }

    fun getRandomImage(){
        isLoading = true;
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
}