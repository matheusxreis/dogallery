package br.com.matheusxreis.dogallery.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class RootScreens(val name:String, val icon: ImageVector){

    object MAIN: RootScreens("main", Icons.Rounded.Search)
    object GALLERY: RootScreens("gallery", Icons.Rounded.Favorite)
}
