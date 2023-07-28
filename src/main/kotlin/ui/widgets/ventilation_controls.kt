package ui.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// TODO: replace button text with icons

@Composable
fun VentilationControls(modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Ventilation", fontSize = 40.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            VentilationButton(onClick = {}, modifier = Modifier.size(100.dp)) {
                Text("Medium")
            }
            VentilationButton(onClick = {}) {
                Text("High")
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            VentilationButton(onClick = {}) {
                Text("Low")
            }
            VentilationButton(onClick = {}) {
                Text("Timer")
            }
        }
    }
}

@Composable
fun VentilationButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    MirrorButton(modifier = modifier.size(100.dp), onClick = onClick, content = content)
}
