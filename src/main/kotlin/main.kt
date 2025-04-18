import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun main() = application {
    val state = rememberWindowState(
        placement = WindowPlacement.Fullscreen,
        position = WindowPosition.Absolute(2560.dp, 0.dp) // TODO: For debug purposes only (second monitor)
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "Smart Mirror",
        state = state,
        onKeyEvent = {
            if (it.type != KeyEventType.KeyUp) {
                return@Window false;
            }

            if (it.key == Key.Escape) {
                this.exitApplication()
                return@Window true
            }
            if (it.key == Key.F11) {
                if (state.placement == WindowPlacement.Fullscreen) {
                    // TODO: This is simply not working. JB pls fix
                    state.placement = WindowPlacement.Maximized
                } else {
                    state.placement = WindowPlacement.Fullscreen
                }
                return@Window true
            }
            return@Window false;
        }
    ) {
        App()
    }
}
