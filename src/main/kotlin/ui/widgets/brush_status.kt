package ui.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hw.BrushScanner
import hw.BrushState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.math.abs

const val progressSize = 15f
const val progressOutline = 2f

const val maxTimer = 2 * 60

// TODO: hook into bluetooth advertisements to start anim
// TODO: Add tooth graphic

@ExperimentalCoroutinesApi
@Composable
fun BrushStatus(modifier: Modifier = Modifier) {
    val color = MaterialTheme.colors.primary

//    var isRunning by remember { mutableStateOf(false) }
    var timer by remember { mutableStateOf(0) }

    val animateFloat = remember { Animatable(0f) }

    // TODO: figure out how to effectively combine local timer plus time from brush
//    LaunchedEffect(true) {
//        BrushScanner
//            .onBrushUpdate()
//            .collectLatest {
//                isRunning = it.state == BrushState.RUN
//
//                // Override timer if it's too far behind
//                if (abs(timer - it.timer) > 2) {
//                    println("Overriding timer...")
//                    timer = it.timer
//                }
//            }
//    }
//
    LaunchedEffect(true) {
            while (timer < maxTimer) {
                animateFloat.animateTo(
                    targetValue = ((timer + 1.0f) / maxTimer),
                    animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
                )
                timer++
            }
    }

    Box(modifier.fillMaxSize()) {
        Canvas(Modifier.fillMaxSize()) {
            val arcCenter =
                center - Offset(
                    size.minDimension / 2 - (progressSize / 2),
                    size.minDimension / 2 - (progressSize / 2)
                )
            drawArc(
                color,
                -90f,
                360f * animateFloat.value,
                false,
                topLeft = arcCenter,
                size = Size(size.minDimension - (progressSize), size.minDimension - (progressSize)),
                style = Stroke(width = progressSize)
            )
            drawCircle(color = color, radius = size.minDimension / 2, style = Stroke(width = progressOutline))
            drawCircle(
                color = color,
                radius = size.minDimension / 2 - progressSize,
                style = Stroke(width = progressOutline)
            )
        }
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