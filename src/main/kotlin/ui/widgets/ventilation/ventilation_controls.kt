package ui.widgets.ventilation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import hw.Ventilation

@Composable
fun VentilationControls(modifier: Modifier = Modifier) {

    LaunchedEffect(true) {
//        Ventilation.startDebugMonitor()
    }

    Column(modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text("Ventilation", fontSize = 40.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            VentilationModeButton(VentilationMode.MEDIUM)
            VentilationModeButton(VentilationMode.HIGH)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            VentilationModeButton(VentilationMode.LOW)
            VentilationTimerButton()
        }
    }
}