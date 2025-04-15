import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tryapp.business.music.MusicServiceViewModel

@Composable
fun MusicControlSegmentedButtons(musicServiceViewModel: MusicServiceViewModel) {

    val isPlaying by musicServiceViewModel.isPlaying.collectAsState()

    var selectedIndex by remember { mutableIntStateOf(0) }
    var sliderPosition by remember { mutableFloatStateOf(0f) }

    val labels = listOf("Prev", if (isPlaying) "Stop" else "Start", "Next")

    Column {
        Slider(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = sliderPosition,
            onValueChange = { sliderPosition = it }
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            Text(text = sliderPosition.toString())
            Text(text = sliderPosition.toString())
        }

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            labels.forEachIndexed { index, labelText ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index, labels.size),
                    onClick = {
                        selectedIndex = index
                        when (labelText) {
                            "Start" -> musicServiceViewModel.startMusic()
                            "Stop" -> musicServiceViewModel.stopMusic()
                            "Prev" -> { /* todo */
                            }

                            "Next" -> { /* todo */
                            }
                        }
                    },
                    selected = index == selectedIndex,
                    icon = {
                        val icon = when (labelText) {
                            "Prev" -> Icons.Filled.SkipPrevious
                            "Start" -> Icons.Filled.PlayArrow
                            "Stop" -> Icons.Filled.Pause
                            "Next" -> Icons.Filled.SkipNext
                            else -> Icons.Filled.PlayArrow
                        }
                        Icon(imageVector = icon, contentDescription = labelText)
                    },
                    label = { Text("") }
                )
            }
        }
    }
}
