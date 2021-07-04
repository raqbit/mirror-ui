import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.desktop.AppWindow
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.unit.IntSize
import java.awt.Color.BLACK
import javax.swing.SwingUtilities


@ExperimentalAnimationApi
fun main() = SwingUtilities.invokeLater {
    val window = AppWindow(
        title = "Smart Mirror",
        size = IntSize(1920, 1080)
    )
    window.window.background = BLACK
    window.window.contentPane.background = BLACK

    // Add escape as close shortcut
    window.keyboard.setShortcut(Key.Escape) {
        window.close()
    }

    window.keyboard.setShortcut(Key.F11) {
        if(window.isFullscreen) {
            window.restore()
        } else {
            window.makeFullscreen()
        }
    }

    // Immediately go into fullscreen
    window.makeFullscreen()

    window.show {
        App()
    }
}