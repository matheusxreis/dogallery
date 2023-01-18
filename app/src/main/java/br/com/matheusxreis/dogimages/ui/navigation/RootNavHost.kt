package br.com.matheusxreis.dogimages.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.matheusxreis.dogimages.ui.screens.GalleryScreen
import br.com.matheusxreis.dogimages.ui.screens.MainScreen

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun RootNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination:String = RootScreens.MAIN.name
) {

  NavHost(navController = navController,
  startDestination = startDestination ){
      composable(route = RootScreens.MAIN.name){
          MainScreen()
      }
      composable(route = RootScreens.GALLERY.name){
          GalleryScreen()
      }
  }


}
