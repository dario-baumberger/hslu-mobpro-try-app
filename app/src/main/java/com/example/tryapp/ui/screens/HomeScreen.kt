import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.tryapp.TryApplicationScreens
import com.example.tryapp.ui.components.Title

@Composable
fun HomeScreen(
    name: String = "Test",
    navController: NavController,
    isFirstLaunch: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {

    val shouldSlide = !isFirstLaunch.value
    var isVisible by remember { mutableStateOf(false) }



    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = if (shouldSlide) slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(300)
        ) else fadeIn(animationSpec = tween(300)),
        exit = slideOutHorizontally(
            targetOffsetX = { -it },
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
                Title("Home")
                Text("Welcome :)")
                Button(
                    onClick = {
                        navController.navigate("${TryApplicationScreens.Detail.name}/HomeScreen")
                    },
                ) {
                    Text("Laden")
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    onClick = {
                        navController.navigate("${TryApplicationScreens.Detail.name}/HomeScreen")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)

                ) {
                    Text("Detail")
                }
                Button(
                    onClick = {
                        navController.navigate("${TryApplicationScreens.Bands.name}")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)

                ) {
                    Text("Bands")
                }
                Text(
                    "With the button above, we can navigate to a new screen",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun HomePreview() {
    Text("Hallo")
}