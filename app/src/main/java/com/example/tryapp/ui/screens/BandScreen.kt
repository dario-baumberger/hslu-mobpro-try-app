import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.tryapp.ui.bands.BandsViewModel
import com.example.tryapp.ui.components.Title

@Composable
fun BandScreen(
    bandCode: String,
    navController: NavController,
    isFirstLaunch: MutableState<Boolean>,
    bandsViewModel: BandsViewModel,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(bandCode) {
        bandsViewModel.requestBandInfoFromServer(bandCode)
        isVisible = true
        isFirstLaunch.value = false
    }

    val currentBand by bandsViewModel.currentBand.collectAsState(initial = null)

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
                if (currentBand == null) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Loading band...",
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    Title(currentBand!!.name)

                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "${currentBand!!.homeCountry}, ${currentBand!!.foundingYear}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    AsyncImage(
                        modifier = Modifier.padding(8.dp),
                        model = currentBand!!.bestOfCdCoverImageUrl,
                        contentDescription = null
                    )
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
