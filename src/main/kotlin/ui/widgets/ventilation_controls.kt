package ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// TODO: replace button text with icons

@Composable
fun VentilationControls(modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Ventilation", fontSize = 40.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            VentilationButton(onClick = {}, modifier = Modifier.size(100.dp)) {
                Image(painterResource("auto.svg"), "Auto", modifier=Modifier.size(60.dp))

            }
            VentilationButton(onClick = {}) {
                Image(painterResource("fan.svg"), "On", modifier=Modifier.size(60.dp))
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            VentilationButton(onClick = {}) {
                Image(painterResource("fan_off.svg"), "Off", modifier=Modifier.size(60.dp))
            }
            VentilationButton(onClick = {}) {
                Image(painterResource("timer.svg"), "Timer", modifier=Modifier.size(60.dp))
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
