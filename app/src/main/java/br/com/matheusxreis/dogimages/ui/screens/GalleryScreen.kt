package br.com.matheusxreis.dogimages.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.matheusxreis.dogimages.ui.viewmodels.GalleryViewModel
import coil.compose.AsyncImage


@Composable
fun GalleryScreen(galleryViewModel: GalleryViewModel = viewModel()){

    val images = galleryViewModel.images;

    LaunchedEffect(Unit, {
        galleryViewModel.getImages()
    } )

    Column(modifier = Modifier.fillMaxSize()) {
        images.map {
            Text(text = it.id.toString())
            AsyncImage(
                model=it.url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .shadow(2.dp)
                    .clip(
                        RoundedCornerShape(2.dp)
                    )
            )
        }
    }

}