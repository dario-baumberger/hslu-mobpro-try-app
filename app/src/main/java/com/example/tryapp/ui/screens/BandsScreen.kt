package com.example.tryapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
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
import com.example.tryapp.TryApplicationScreens
import com.example.tryapp.business.bands.BandsViewModel
import com.example.tryapp.business.music.MusicServiceViewModel

@Composable
fun BandsScreen(
    navController: NavController,
    musicServiceViewModel: MusicServiceViewModel,
    modifier: Modifier = Modifier,

    ) {
    val viewModel: BandsViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.requestBandCodesFromServer()

    }

    val bands by viewModel.bandsFlow.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {


            if (bands.isEmpty()) {
                Text("Loading bands...", modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(bands) { band ->
                        val name = band.name ?: "Unnamed Band"
                        val code = band.code

                        if (!code.isNullOrBlank()) {
                            ListItem(
                                headlineContent = { Text(name) },
                                supportingContent = { Text("Tap for more info") },
                                leadingContent = {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate("${TryApplicationScreens.Band.route}/$code")
                                    }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Button(
                onClick = {
                    navController.navigate(TryApplicationScreens.Music.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Text("Music")
            }
        }
    }
}
