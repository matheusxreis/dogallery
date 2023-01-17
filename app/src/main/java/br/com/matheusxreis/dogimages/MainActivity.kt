package br.com.matheusxreis.dogimages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
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
    var lastImageUrl = mainViewModel.lastImageUrl;
    var isLoadingData = mainViewModel.isLoading;

    Column(modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
       when(isLoadingData){
            true -> CircularProgressIndicator();
            false -> {
                if(actualImageUrl.length>0){
                    Text(text = "Actual image: ${mainViewModel.actualImageUrl}")
                    Spacer(modifier = Modifier.height(7.dp))
                    AsyncImage(
                        model=actualImageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(300.dp)
                            .height(300.dp)
                            .shadow(2.dp)
                            .clip(
                                RoundedCornerShape(7.dp)
                            )
                    )
                }else {
                    Text(text="No image yet")
                }
               
           }
       }
    Spacer(modifier = Modifier.height(7.dp))
    MyButton(onClick = { mainViewModel.getRandomImage() },
        enabled = !isLoadingData,
        icon = Icons.Rounded.Search,
        text = if(actualImageUrl.length>0) "Search Again".uppercase() else "Search".uppercase()
    )

    if(lastImageUrl.length>0){
        MyButton(onClick = { mainViewModel.comeBackImage() },
            text = "Come back image",
            enabled = !isLoadingData,
            secondary = true,
            icon = Icons.Rounded.ArrowBack
            )
    }
    }
}

@Composable
fun MyButton(
    onClick: ()->Unit,
    text: String,
    icon: ImageVector,
    enabled: Boolean,
    secondary: Boolean = false
){
    Button(onClick = onClick,
    enabled = enabled,
    colors = ButtonDefaults.buttonColors(
    containerColor= if(secondary) Color.LightGray else MaterialTheme.colorScheme.primary,
),
    modifier = Modifier.fillMaxWidth()) {
        Icon(icon, contentDescription = "", tint = MaterialTheme.colorScheme.background)

        Text(text = text.uppercase())
    }
}
