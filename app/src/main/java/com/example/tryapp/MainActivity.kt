package com.example.tryapp

import BandScreen
import BandsScreen
import HomeScreen
import SplashScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tryapp.business.bands.BandsViewModel
import com.example.tryapp.ui.screens.DetailScreen
import com.example.tryapp.ui.theme.TryAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TryAppTheme {
                val navController = rememberNavController()


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val globalModifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()

                    TryNavHost(
                        navHostController = navController,
                        modifier = globalModifier
                    )
                }
            }
        }
    }
}

@Composable
fun TryNavHost(
    navHostController: NavHostController,
    modifier: Modifier,
) {
    val isFirstLaunch = remember { mutableStateOf(true) }
    val bandsViewModel: BandsViewModel = viewModel()

    NavHost(
        navController = navHostController,
        startDestination = TryApplicationScreens.Splash.name,
        modifier = Modifier.padding(16.dp),

        ) {
        composable(route = TryApplicationScreens.Splash.name) {
            SplashScreen(navController = navHostController, isFirstLaunch)
        }
        composable(route = TryApplicationScreens.Home.name) {
            HomeScreen("Home Screen", navHostController, isFirstLaunch)

        }
        composable(
            route = "${TryApplicationScreens.Detail.name}/{senderText}",
            arguments = listOf(
                navArgument("senderText") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val senderText = navBackStackEntry.arguments?.getString("senderText") ?: "error"
            DetailScreen(senderText = senderText, navHostController, isFirstLaunch)
        }
        composable(route = TryApplicationScreens.Bands.name) {

            BandsScreen(navHostController, isFirstLaunch, bandsViewModel = bandsViewModel)

        }
        composable(route = "${TryApplicationScreens.Band.name}/{bandCode}", arguments = listOf(
            navArgument("bandCode") { type = NavType.StringType }
        )) { navBackStackEntry ->
            val bandCode = navBackStackEntry.arguments?.getString("bandCode") ?: "error"
            BandScreen(
                bandCode = bandCode,
                navHostController,
                isFirstLaunch,
                bandsViewModel = bandsViewModel
            )
        }
    }
}


enum class TryApplicationScreens {
    Home,
    Detail,
    Splash,
    Bands,
    Band
}
