package com.example.tryapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tryapp.TryApplicationScreens
import com.example.tryapp.withArgs
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    name: String = "Test",
    navController: NavController,
    isFirstLaunch: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    var showSplash by remember { mutableStateOf(isFirstLaunch.value) }
    var isSplashVisible by remember { mutableStateOf(false) }
    var showHomeContent by remember { mutableStateOf(!isFirstLaunch.value) }

    // Run splash animation once on first launch
    LaunchedEffect(showSplash) {
        if (showSplash) {
            isSplashVisible = true
            delay(1000)
            isSplashVisible = false
            delay(300)
            showSplash = false
            showHomeContent = true
            isFirstLaunch.value = false
        }
    }

    // Splash animation
    AnimatedVisibility(
        visible = isSplashVisible,
        enter = fadeIn(tween(500)),
        exit = fadeOut(tween(300))
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Loading...", style = MaterialTheme.typography.headlineLarge)
        }
    }

    // Home content (only fade in/out now)
    AnimatedVisibility(
        visible = showHomeContent,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    modifier = Modifier
                        .size(width = 240.dp, height = 100.dp)
                ) {
                    Text(
                        text = "Filled",
                        modifier = Modifier
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                    )
                }
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    modifier = Modifier
                        .size(width = 240.dp, height = 100.dp)
                ) {
                    Text(
                        text = "Filled",
                        modifier = Modifier
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    onClick = {
                        navController.navigate(TryApplicationScreens.Detail.withArgs("HomeScreen"))
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Detail")
                }
                Button(
                    onClick = {
                        navController.navigate("${TryApplicationScreens.Sms.route}")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Sms")
                }
                Text(
                    "With the button above, we can navigate to a new screen",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
