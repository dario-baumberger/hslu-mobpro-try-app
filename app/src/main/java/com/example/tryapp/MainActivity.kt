package com.example.tryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.tryapp.business.bands.BandsViewModel
import com.example.tryapp.business.music.MusicServiceViewModel
import com.example.tryapp.ui.components.BottomNavigation
import com.example.tryapp.ui.components.TopBar
import com.example.tryapp.ui.theme.TryAppTheme
import com.example.tryapp.ui.viewmodel.AppStateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TryAppTheme {
                val navController = rememberNavController()
                val bandsViewModel: BandsViewModel = hiltViewModel()
                val appStateViewModel: AppStateViewModel = hiltViewModel()
                val activity = LocalActivity.current as ComponentActivity
                val musicServiceViewModel: MusicServiceViewModel = hiltViewModel(activity)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar(navController = navController, musicServiceViewModel)
                    },
                    bottomBar = {
                        BottomNavigation(
                            navController = navController,
                        )
                    }
                ) { paddingValues ->
                    TryNavHost(
                        navHostController = navController,
                        appStateViewModel = appStateViewModel,
                        bandsViewModel = bandsViewModel,
                        musicServiceViewModel = musicServiceViewModel,
                        modifier = Modifier
                            .padding(paddingValues)
                            .padding(16.dp)
                            .fillMaxSize(),
                    )
                }
            }
        }
    }
}


