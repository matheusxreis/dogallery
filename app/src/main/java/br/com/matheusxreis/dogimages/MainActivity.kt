package br.com.matheusxreis.dogimages

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.matheusxreis.dogimages.ui.theme.DogImagesTheme
import br.com.matheusxreis.dogimages.ui.viewmodels.MainViewModel
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
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

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    var actualImageUrl = mainViewModel.actualImageUrl;
    var lastImageUrl = mainViewModel.lastImageUrl;
    var isLoadingData = mainViewModel.isLoading;
    var isConnected = mainViewModel.isConnected;


    Column(modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        if(!isConnected){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(7.dp))
                    .height(25.dp)
                    .background(color = MaterialTheme.colorScheme.errorContainer)){
                Icon(Icons.Filled.Warning, contentDescription = "", tint=MaterialTheme.colorScheme.error)
                Text(text = stringResource(id = R.string.not_net))
            }
            Spacer(modifier = Modifier.height(7.dp))
        }
        when(isLoadingData){
            true -> CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            );
            false -> {
                if(actualImageUrl.length>0){

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
                    if(isConnected) {
                        Text(
                            text = stringResource(id = R.string.sad_today),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(id = R.string.try_dog_images),
                            fontWeight = FontWeight.Light
                        )
                    }
                }
               
           }
       }
    Spacer(modifier = Modifier.height(7.dp))
    MyButton(onClick = { mainViewModel.getRandomImage() },
        enabled = !isLoadingData,
        icon = if(!isConnected) Icons.Rounded.Refresh else Icons.Rounded.Search,
        text = if(!isConnected) stringResource(id = R.string.try_again) else if(actualImageUrl.length>0) stringResource(id = R.string.search_again).uppercase() else stringResource(
            id = R.string.search
        ).uppercase()
    )

    if(lastImageUrl.length>0){
        MyButton(onClick = { mainViewModel.comeBackImage() },
            text = stringResource(id = R.string.come_back_image),
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
