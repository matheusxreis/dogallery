package br.com.matheusxreis.dogimages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.matheusxreis.dogimages.ui.theme.DogImagesTheme
import br.com.matheusxreis.dogimages.ui.viewmodels.MainViewModel
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogImagesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    var actualImageUrl = mainViewModel.actualImageUrl;
    var isLoadingData = mainViewModel.isLoading;


    Column() {

       when(isLoadingData){
            true -> Text("loading...................");
            false -> {
                if(actualImageUrl.length>0){
                    Text(text = "Actual image: ${mainViewModel.actualImageUrl}")
                    AsyncImage(
                        model=actualImageUrl,
                        contentDescription = null
                    )
                }else {
                    Text(text="No image yet")
                }
                Button(onClick = { mainViewModel.getRandomImage() }) {
                    Text(text = "Buscar")
                }
           }
       }
    }
}
