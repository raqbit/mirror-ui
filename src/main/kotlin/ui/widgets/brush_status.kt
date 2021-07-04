package ui.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

const val progressSize = 15f
const val progressOutline = 2f

// TODO: hook into bluetooth advertisements to start anim
// TODO: Add tooth graphic

@Composable
fun BrushStatus(modifier: Modifier = Modifier) {
    val color = MaterialTheme.colors.primary

    var timeRemaining by remember { mutableStateOf(60 * 2) }

    // Countdown
    LaunchedEffect(timeRemaining) {
        while (timeRemaining > 0) {
            delay(1000);
            timeRemaining--
        }
    }

    val animateFloat = remember { Animatable(0f) }
    LaunchedEffect(animateFloat) {
        animateFloat.animateTo(
            targetValue = 0.25f,
            animationSpec = tween(durationMillis = 30 * 1000, easing = LinearEasing)
        )
        animateFloat.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(durationMillis = 30 * 1000, easing = LinearEasing)
        )
        animateFloat.animateTo(
            targetValue = 0.75f,
            animationSpec = tween(durationMillis = 30 * 1000, easing = LinearEasing)
        )
        animateFloat.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 30 * 1000, easing = LinearEasing)
        )
    }

    Box(modifier.fillMaxSize()) {
        Canvas(modifier.fillMaxSize()) {
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
        // TODO: not aligned properly
        Text(formatTimeRemaining(timeRemaining), fontSize = 32.sp, modifier = modifier.align(Alignment.BottomCenter))
    }
}

fun formatTimeRemaining(timeRemaining: Int): String {
    return "${(timeRemaining / 60).toString().padStart(2, '0')}:${(timeRemaining % 60).toString().padStart(2, '0')}"
}