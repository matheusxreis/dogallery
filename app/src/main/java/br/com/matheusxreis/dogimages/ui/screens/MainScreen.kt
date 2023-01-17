package br.com.matheusxreis.dogimages.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.matheusxreis.dogimages.ui.components.MyButton
import br.com.matheusxreis.dogimages.ui.viewmodels.MainViewModel
import coil.compose.AsyncImage
import br.com.matheusxreis.dogimages.R

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    var actualImageUrl = mainViewModel.actualImageUrl;
    var lastImageUrl = mainViewModel.lastImageUrl;
    var isLoadingData = mainViewModel.isLoading;
    var isConnected = mainViewModel.isConnected;
    var imageWasSaved = mainViewModel.imageWasSaved;
    var amountSaved = mainViewModel.amountImagesSaved;

    var savingError by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(imageWasSaved){
        savingError = !imageWasSaved
    }


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
                Icon(Icons.Filled.Warning, contentDescription = "", tint= MaterialTheme.colorScheme.error)
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

        if(actualImageUrl.length>0){
            MyButton(onClick = { mainViewModel.saveImageInStorage(actualImageUrl) },
                text = if(savingError) "Try again" else "Save",
                icon = if(savingError) Icons.Rounded.Refresh else Icons.Rounded.Star,
                enabled = actualImageUrl.length>0)
        }
        if(lastImageUrl.length>0){
            MyButton(onClick = { mainViewModel.comeBackImage() },
                text = stringResource(id = R.string.come_back_image),
                enabled = !isLoadingData,
                secondary = true,
                icon = Icons.Rounded.ArrowBack
            )
        }

        Spacer(modifier = Modifier.height(7.dp))
        if(!isLoadingData){
            Row(){
                Text(text = "Amount of puppies saved: ${amountSaved}")
            }
        }

    }
}
