import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ui.theme.MirrorTheme
import ui.widgets.BrushStatus
import ui.widgets.TimeDateDisplay
import ui.widgets.VentilationControls


@ExperimentalAnimationApi
@Composable
fun App() {
    var visible by remember { mutableStateOf(false) }

    // TODO: instead, delay until everything has loaded?
    LaunchedEffect(true) {
        delay(200)
        visible = true
    }

    val enterAnim = fadeIn(0F, spring(stiffness = Spring.StiffnessVeryLow))
    val exitAnim = fadeOut(0F, spring(stiffness = Spring.StiffnessVeryLow))

    MirrorTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            AnimatedVisibility(visible, enter = enterAnim, exit = exitAnim) {
                Box(Modifier.fillMaxSize().padding(40.dp)) {
                    TimeDateDisplay(modifier = Modifier.align(Alignment.TopCenter).padding(top = 180.dp))
                    BrushStatus(modifier = Modifier.align(Alignment.TopStart).size(300.dp))
                    VentilationControls(
                        modifier = Modifier.align(Alignment.BottomStart),
                    )
                }
            }
        }
    }
}
