package com.example.tryapp.ui.components

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tryapp.TryApplicationScreens
import com.example.tryapp.business.music.MusicServiceViewModel
import com.example.tryapp.data.MusicPlayerService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavHostController,
    musicServiceViewModel: MusicServiceViewModel
) {
    val context = LocalContext.current

    val isPlaying by musicServiceViewModel.isPlaying.collectAsState()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val topLevelScreens = listOf(
        TryApplicationScreens.Home.route,
        TryApplicationScreens.Users.route,
        TryApplicationScreens.Bands.route
    )

    // Get current screen's title (based on route)


    TopAppBar(
        title = { Text(currentRoute.toString().uppercase()) },
        navigationIcon = {
            if (!topLevelScreens.contains(currentRoute)) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go back"
                    )
                }
            }
        },
        actions = {
            if (
                currentRoute?.startsWith(TryApplicationScreens.Bands.route) == true ||
                currentRoute?.startsWith(TryApplicationScreens.Band.route) == true
            ) {
                IconButton(onClick = {
                    val intent = Intent(context, MusicPlayerService::class.java)
                    if (isPlaying) {
                        context.stopService(intent)
                        musicServiceViewModel.stopMusic()
                    } else {
                        ContextCompat.startForegroundService(context, intent)
                        musicServiceViewModel.startMusic()
                    }
                }) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = if (isPlaying) "Stop Music" else "Start Music"
                    )
                }
            }
        }
    )
}
