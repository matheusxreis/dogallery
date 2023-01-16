package br.com.matheusxreis.dogimages.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.matheusxreis.dogimages.domain.useCases.IGetRandomImageUseCase
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {

    lateinit var getRandomImageUseCase: IGetRandomImageUseCase;

    var actualImageUrl by mutableStateOf("")
        private set;
    var lastImageUrl by mutableStateOf("")
        private set;

    init {

    }

    fun getRandomImage(){
        viewModelScope.launch {
            var url = getRandomImageUseCase.execute()
            if(lastImageUrl.length>0) {
                lastImageUrl = actualImageUrl;
            }
             actualImageUrl = url;

       }
    }
}