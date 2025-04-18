package ui.widgets.brush_status

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// TODO: hook into bluetooth advertisements to start anim
// TODO: Add tooth graphic

@Composable
fun BrushStatus(modifier: Modifier = Modifier) {
    var timer by remember { mutableStateOf(60 * 2) }

    // Countdown
    LaunchedEffect(timer) {
        while (timer > 0) {
            delay(1000);
            timer--
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

    ProgressRing(animateFloat.value, modifier=modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center),
        ) {
            Image(
                painterResource("tooth.svg"), "Tooth",
                modifier = Modifier
                    .size(120.dp)
            )
            Text(
                formatTimeRemaining(timer),
                fontSize = 32.sp,
            )
        }
    }
}

fun formatTimeRemaining(timeRemaining: Int): String {
    return "${(timeRemaining / 60).toString().padStart(2, '0')}:${(timeRemaining % 60).toString().padStart(2, '0')}"
}
