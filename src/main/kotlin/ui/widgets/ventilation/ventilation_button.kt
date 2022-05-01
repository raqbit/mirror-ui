package ui.widgets.ventilation

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
//import hw.Ventilation
import kotlinx.coroutines.delay
import ui.widgets.MirrorButton

enum class VentilationMode {
    HIGH,
    MEDIUM,
    LOW,
}

@Composable
private fun VentilationButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    MirrorButton(
        modifier = modifier.size(100.dp),
        interactionSource = interactionSource,
        onClick = onClick,
        content = content
    )
}

// TODO: replace button text with icons

@Composable
fun VentilationModeButton(mode: VentilationMode) {
    VentilationButton(onClick = {
//        Ventilation.mode = mode;
    }) {
        when (mode) {
            VentilationMode.HIGH -> Text("High")
            VentilationMode.MEDIUM -> Text("Medium")
            VentilationMode.LOW -> Text("Low")
        }
    }
}

@Composable
fun VentilationTimerButton() {
    val interactionSource = remember { MutableInteractionSource() }
    var timer by remember { mutableStateOf(0) }
    val held by interactionSource.collectIsPressedAsState()

    LaunchedEffect(held) {
        if (held) {
            timer = 1;
            while (timer < 3) {
                delay(1000)
                timer++
            }
        } else {
            // TODO: activate timer of correct length
            timer = 0;
        }
    }

    VentilationButton(
        interactionSource = interactionSource
    ) {
        when (timer) {
            1 -> Text("10min")
            2 -> Text("20min")
            3 -> Text("30min")
            else -> Text("Timer")
        }
    }
}