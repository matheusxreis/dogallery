package br.com.matheusxreis.dogimages.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.matheusxreis.dogimages.ui.components.MyButton
import br.com.matheusxreis.dogimages.ui.viewmodels.MainViewModel
import coil.compose.AsyncImage
import br.com.matheusxreis.dogimages.R
import br.com.matheusxreis.dogimages.ui.components.MyDialog
import br.com.matheusxreis.dogimages.ui.providers.NotificationLocalOf

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    var actualImageUrl = mainViewModel.actualImageUrl;
    var lastImageUrl = mainViewModel.lastImageUrl;
    var isLoadingData = mainViewModel.isLoading;
    var isConnected = mainViewModel.isConnected;
    var imageWasSaved = mainViewModel.imageWasSaved;
    var amountSaved = mainViewModel.amountImagesSaved;

    var notifications = NotificationLocalOf.current

    var savingError by remember {
        mutableStateOf(false)
    }
    var alreadySaved by remember {
        mutableStateOf(false)
    }

    var dialogState by remember {
        mutableStateOf(false)
    }

    fun saveImage(){
        mainViewModel.saveImageInStorage(actualImageUrl) { notifications.increment(); };
        alreadySaved = mainViewModel.alreadyIsSaved(actualImageUrl)
    }


    LaunchedEffect(imageWasSaved){
        savingError = !imageWasSaved
    }
    LaunchedEffect(actualImageUrl){
        alreadySaved = mainViewModel.alreadyIsSaved(actualImageUrl)
    }

    Column(modifier = Modifier
        .background(color = MaterialTheme.colorScheme.background)
        .fillMaxSize()) {
        Column(modifier = Modifier.padding(horizontal = 24.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {



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
                            .clickable {
                                dialogState = true
                            }
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
        MyButton(onClick = { alreadySaved = false; mainViewModel.getRandomImage() },
            enabled = !isLoadingData,
            icon = if(!isConnected) Icons.Rounded.Refresh else Icons.Rounded.Search,
            text = if(!isConnected) stringResource(id = R.string.try_again) else if(actualImageUrl.length>0) stringResource(id = R.string.search_again).uppercase() else stringResource(
                id = R.string.search
            ).uppercase()
        )

        if(actualImageUrl.length>0){
            MyButton(onClick = { saveImage() },
                text = if(savingError) stringResource(id = R.string.try_again) else stringResource(id = R.string.save),
                icon = if(savingError) Icons.Rounded.Refresh else Icons.Outlined.Favorite,
                enabled = !alreadySaved
            )
        }
        if(lastImageUrl.length>0){
            MyButton(onClick = { mainViewModel.comeBackImage() },
                text = stringResource(id = R.string.come_back_image),
                enabled = !isLoadingData,
                color = MaterialTheme.colorScheme.secondary,
                icon = Icons.Rounded.ArrowBack
            )
        }

        Spacer(modifier = Modifier.height(7.dp))
        if(!isLoadingData){
            Row(){
                Text(text = "${stringResource(id = R.string.amount_of_puppies)}: ${amountSaved}")
            }
        }



        MyDialog(visible = dialogState, onDismissRequest = { dialogState = false }) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .background(color = Color.Transparent)
                    .clickable { dialogState = false }
            ) {
                AsyncImage(
                    model = actualImageUrl,
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth(1f)
                )
                Column(
                    modifier = Modifier.fillMaxWidth(1f)
                ) {

                    if(!alreadySaved){
                        MyButton(onClick = { saveImage() },
                            text = if(savingError) stringResource(id = R.string.try_again) else stringResource(id = R.string.save),
                            icon = if(savingError) Icons.Rounded.Refresh else Icons.Outlined.Favorite,
                            enabled = true
                        )
                    }


                }

            }
        }
       }
    }
}
