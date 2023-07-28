package ui.widgets.brush_status

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val progressSize = 15f
const val progressOutline = 2f

const val maxTimer = 2 * 60

// TODO: hook into bluetooth advertisements to start anim
// TODO: Add tooth graphic

@Composable
fun BrushStatus(modifier: Modifier = Modifier) {
//    var isRunning by remember { mutableStateOf(false) }
    var timer by remember { mutableStateOf(0) }

    val animateFloat = remember { Animatable(0f) }

    // TODO: figure out how to effectively combine local timer plus time from brush
//    LaunchedEffect(true) {
//        BrushScanner
//            .onBrushUpdate()
//            .collectLatest {
//                timer = it
//                animateFloat.animateTo(
//                    targetValue = ((timer + 1.0f) / maxTimer),
//                    animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
//                )
//            }
//    }

    Box(modifier.fillMaxSize()) {
        BrushStatusRing(animateFloat.value);
        Box(
            Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(progressSize.dp * 2f)
        ) {
            Text(
                formatTimeRemaining(timer),
                fontSize = 32.sp,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

fun formatTimeRemaining(timeRemaining: Int): String {
    return "${(timeRemaining / 60).toString().padStart(2, '0')}:${(timeRemaining % 60).toString().padStart(2, '0')}"
}
