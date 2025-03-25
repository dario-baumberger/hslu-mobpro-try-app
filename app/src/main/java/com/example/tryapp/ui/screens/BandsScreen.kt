import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.tryapp.TryApplicationScreens
import com.example.tryapp.business.bands.BandsViewModel
import com.example.tryapp.ui.components.Title

@Composable
fun BandsScreen(
    navController: NavController, isFirstLaunch: MutableState<Boolean>,
    bandsViewModel: BandsViewModel,
    modifier: Modifier = Modifier
) {


    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        bandsViewModel.requestBandCodesFromServer()
        isVisible = true
        isFirstLaunch.value = false
    }

    val bands by bandsViewModel.bandsFlow.collectAsState()

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(300)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(300)
        )
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Title("Bands")
                if (bands.isEmpty()) {
                    Text("Loading bands...")
                } else {
                    bands.forEach { band ->


                        Text(
                            modifier = Modifier.clickable { navController.navigate(route = "${TryApplicationScreens.Band.name}/${band.code}") },
                            text = band.name
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Go Back")

                }


            }
        }
    }


}

