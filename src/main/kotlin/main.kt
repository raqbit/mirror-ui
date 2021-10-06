import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.geometry.Size
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
                    state.placement = WindowPlacement.Floating
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
//    window.window.background = BLACK
//    window.window.contentPane.background = BLACK
}