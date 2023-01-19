package br.com.matheusxreis.dogimages.ui.screens

import android.app.Activity
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.matheusxreis.dogimages.ui.viewmodels.GalleryViewModel
import coil.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import br.com.matheusxreis.dogimages.domain.entities.Image
import br.com.matheusxreis.dogimages.ui.components.MyButton
import br.com.matheusxreis.dogimages.R
import br.com.matheusxreis.dogimages.ui.providers.NotificationLocalOf
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun GalleryScreen(galleryViewModel: GalleryViewModel = viewModel()){

    val images = galleryViewModel.images;
    var actualImage by remember {
        mutableStateOf<Image?>(null)
    }
    var dialogState by remember {
        mutableStateOf(false)
    }

    var initialRender by remember {
        mutableStateOf(true)
    }

    val downloadingImage = galleryViewModel.downloading
    val notifications = NotificationLocalOf.current
    val context = LocalContext.current

    fun openDialog(it:Image){
        actualImage = Image(it.id, it.url, it.savedAt)
        dialogState = true
    }
    fun downloadImage(url:String){
        galleryViewModel.downloadImage(url)
    }

    LaunchedEffect(Unit) {
        galleryViewModel.getImages()
        notifications.reset()
    }
    LaunchedEffect(downloadingImage){
        if(!initialRender){
            if(downloadingImage != null && downloadingImage != true){
                Toast.makeText(
                    context,
                    "Download was a success",
                    Toast.LENGTH_LONG
                ).show()
            }
            else if(downloadingImage == null){
                Toast.makeText(
                    context,
                    "Download failed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        initialRender = false;

    }


        when (galleryViewModel.isConnected){
            true ->   Column(modifier = Modifier.fillMaxWidth()) {

                if(images.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 128.dp),
                        verticalArrangement = Arrangement.spacedBy(1.dp),
                        horizontalArrangement = Arrangement.spacedBy(1.dp),
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                    ) {
                        items(images) {
                            AsyncImage(
                                model = it.url,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width((50..300).random().dp)
                                    .height(300.dp)
                                    .clip(
                                        shape = RoundedCornerShape(2.dp)
                                    )
                                    .clickable {
                                        openDialog(it)
                                    }
                            )
                        }
                    }

                    if (dialogState) {
                        Dialog(onDismissRequest = { dialogState = false }) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .background(color = Color.Transparent)
                                    .clickable { dialogState = false }
                            ) {
                                AsyncImage(
                                    model = actualImage!!.url,
                                    contentDescription = "",
                                    modifier = Modifier.fillMaxWidth(1f)
                                )
                                Column(
                                    modifier = Modifier.fillMaxWidth(1f)
                                ) {


                                    MyButton(
                                        onClick = {
                                            galleryViewModel.removeImage(actualImage!!.id); dialogState =
                                            false
                                        },
                                        text = stringResource(id = R.string.delete),
                                        icon = Icons.Rounded.Delete,
                                        color = MaterialTheme.colorScheme.error,
                                        enabled = true
                                    )
                                    MyButton(
                                        onClick = {
                                            downloadImage(actualImage!!.url)
                                        },
                                        text = "Download",
                                        loading = downloadingImage == true,
                                        icon = Icons.Outlined.ArrowDropDown,
                                        enabled = true
                                    )

                                }

                            }


                        }
                    }
                }else {
                    Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){
                        Text(text = stringResource(id = R.string.you_didnt_favorite))

                    }
                }

            }
            else -> Column( modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                Text(text = stringResource(id = R.string.not_net))
                MyButton(onClick = {galleryViewModel.getImages()}, text = stringResource(id = R.string.refresh), icon = Icons.Rounded.Refresh, enabled = true)
            }
        }
     

}