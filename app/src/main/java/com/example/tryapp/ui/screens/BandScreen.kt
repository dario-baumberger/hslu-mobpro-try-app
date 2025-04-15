package com.example.tryapp.ui.screens

import MusicControlSegmentedButtons
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.tryapp.business.bands.BandsViewModel
import com.example.tryapp.business.music.MusicServiceViewModel
import com.example.tryapp.ui.components.Title

@Composable
fun BandScreen(
    bandCode: String,
    navController: NavController,
    bandsViewModel: BandsViewModel,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(bandCode) {
        bandsViewModel.requestBandInfoFromServer(bandCode)

    }
    val activity = LocalActivity.current as ComponentActivity
    val musicServiceViewModel: MusicServiceViewModel = hiltViewModel(activity)
    val currentBand by bandsViewModel.currentBand.collectAsState(initial = null)
    val loadError by bandsViewModel.loadError.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when {
                currentBand == null && loadError == null -> {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Loading band...",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                loadError != null -> {
                    Text(
                        text = "Error loading band: $loadError",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> {
                    Title(currentBand!!.name)

                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "${currentBand!!.homeCountry}, ${currentBand!!.foundingYear}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    AsyncImage(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize(),
                        model = currentBand!!.bestOfCdCoverImageUrl,
                        contentDescription = null
                    )
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MusicControlSegmentedButtons(musicServiceViewModel)
        }
    }
}
