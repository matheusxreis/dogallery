package br.com.matheusxreis.dogimages.ui.screens

import android.os.Build
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
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import br.com.matheusxreis.dogimages.domain.entities.Image
import br.com.matheusxreis.dogimages.ui.components.MyButton
import br.com.matheusxreis.dogimages.R
import br.com.matheusxreis.dogimages.utils.Network

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

    fun openDialog(it:Image){
        actualImage = Image(it.id, it.url, it.savedAt)
        dialogState = true
    }

    LaunchedEffect(Unit) {
        galleryViewModel.getImages()
    }


        when (galleryViewModel.isConnected){
            true ->   Column(modifier = Modifier.fillMaxWidth()) {


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

                if(dialogState){
                    Dialog(onDismissRequest = { dialogState = false }) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .background(color = Color.Transparent)
                                .clickable { dialogState = false }
                        ){
                            AsyncImage(model = actualImage!!.url,
                                contentDescription = "",
                                modifier = Modifier.fillMaxWidth(1f)
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(1f)
                            ){

                                MyButton(onClick = { galleryViewModel.removeImage(actualImage!!.id); dialogState = false },
                                    text = stringResource(id = R.string.delete) ,
                                    icon = Icons.Rounded.Delete,
                                    color = MaterialTheme.colorScheme.error,
                                    enabled = true)
                            }

                        }


                    }
                }

            }
            else -> Column( modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
                Text(text = stringResource(id = R.string.not_net))
                MyButton(onClick = {galleryViewModel.getImages()}, text = "Refresh", icon = Icons.Rounded.Refresh, enabled = true)
            }
        }
     

}