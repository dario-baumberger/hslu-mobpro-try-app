package com.example.tryapp

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tryapp.business.bands.BandsViewModel
import com.example.tryapp.business.music.MusicServiceViewModel
import com.example.tryapp.ui.screens.BandScreen
import com.example.tryapp.ui.screens.BandsScreen
import com.example.tryapp.ui.screens.DetailScreen
import com.example.tryapp.ui.screens.HomeScreen
import com.example.tryapp.ui.screens.MusicScreen
import com.example.tryapp.ui.screens.SmsScreen
import com.example.tryapp.ui.screens.UsersScreen
import com.example.tryapp.ui.viewmodel.AppStateViewModel


@Composable
fun TryNavHost(
    navHostController: NavHostController,
    modifier: Modifier,
    appStateViewModel: AppStateViewModel,
    bandsViewModel: BandsViewModel,
    musicServiceViewModel: MusicServiceViewModel
) {

    val context = LocalContext.current

    val isFirstLaunch = appStateViewModel.isFirstLaunch

    NavHost(
        navController = navHostController,
        startDestination = TryApplicationScreens.Home.route,
        modifier = modifier
    ) {

        composable(route = TryApplicationScreens.Home.route) {
            val intent = Intent("ch.hslu.tryapp.MY_ACTION")
            intent.`package` = "ch.hslu.tryapp"
            context.sendBroadcast(intent)

            HomeScreen("Home Screen", navHostController, isFirstLaunch)
        }
        composable(
            route = "${TryApplicationScreens.Detail.route}",
            arguments = listOf(navArgument("senderText") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val senderText = navBackStackEntry.arguments?.getString("senderText") ?: "error"
            DetailScreen(senderText = senderText, navHostController)
        }
        composable(route = TryApplicationScreens.Bands.route) {
            BandsScreen(navHostController, musicServiceViewModel)
        }
        composable(
            route = "${TryApplicationScreens.Band.route}/{bandCode}",
            arguments = listOf(navArgument("bandCode") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val bandCode = navBackStackEntry.arguments?.getString("bandCode") ?: "error"
            BandScreen(
                bandCode = bandCode,
                navHostController,
                bandsViewModel,
            )
        }
        composable(route = TryApplicationScreens.Users.route) {
            UsersScreen(navHostController)
        }
        composable(route = TryApplicationScreens.Music.route) {
            MusicScreen(navHostController)
        }
        composable(route = TryApplicationScreens.Sms.route) {
            SmsScreen(navHostController)
        }
    }
}