package br.com.matheusxreis.dogimages

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract.Root
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.matheusxreis.dogimages.ui.navigation.RootNavHost
import br.com.matheusxreis.dogimages.ui.navigation.RootScreens
import br.com.matheusxreis.dogimages.ui.providers.NotificationLocalOf
import br.com.matheusxreis.dogimages.ui.providers.Notifications
import br.com.matheusxreis.dogimages.ui.screens.GalleryScreen
import br.com.matheusxreis.dogimages.ui.screens.MainScreen
import br.com.matheusxreis.dogimages.ui.theme.DogImagesTheme


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notifications = Notifications()
        setContent {
            DogImagesTheme {
                // A surface container using the 'background' color from the theme
                CompositionLocalProvider(NotificationLocalOf provides notifications
                ) {
                    Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val screensList = listOf(
                        RootScreens.MAIN,
                        RootScreens.GALLERY
                    )
                    Scaffold(
                        bottomBar = {
                            BottomNavigation(
                                backgroundColor = MaterialTheme.colorScheme.primary
                            ){
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination
                                screensList.forEach{ screen ->
                                    val isCurrentDestination =
                                        currentDestination?.hierarchy?.any { it.route == screen.name } == true
                                    val notifications = NotificationLocalOf.current.getAmount()
                                    BottomNavigationItem(
                                        selected = isCurrentDestination,
                                        icon = {
                                            BadgedBox(badge = {  if(notifications != 0 && screen.name == RootScreens.GALLERY.name) Badge { Text(notifications.toString()) } else null}  ) {
                                                Icon(
                                                    imageVector = screen.icon,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(30.dp),
                                                    tint = if(isCurrentDestination) MaterialTheme.colorScheme.onSecondary else Color.LightGray)}
                                            }
                                      ,
                                        selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                                        unselectedContentColor = MaterialTheme.colorScheme.background,
                                        onClick = {navController.navigate(screen.name) })
                                }

                            }
                        }
                    ){
                        RootNavHost(navController)

                    }
                }
            }
            }
        }
    }
}


