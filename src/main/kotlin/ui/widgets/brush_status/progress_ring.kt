package ui.widgets.brush_status

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

private const val progressSize = 15f
private const val progressOutline = 2f

@Composable
fun ProgressRing(progress: Float, modifier: Modifier, content: @Composable BoxScope.() -> Unit) {
    val color = MaterialTheme.colors.primary

    Canvas(modifier.fillMaxSize()) {
        // Outer circle
        drawCircle(color = color, radius = size.minDimension / 2, style = Stroke(width = progressOutline))

        // Inner circle
        drawCircle(
            color = color,
            radius = size.minDimension / 2 - progressSize,
            style = Stroke(width = progressOutline)
        )

        // Progress
        val arcCenter =
            center - Offset(
                size.minDimension / 2 - (progressSize / 2),
                size.minDimension / 2 - (progressSize / 2)
            )
        drawArc(
            color,
            -90f, 360f * progress,
            false, topLeft = arcCenter,
            size = Size(size.minDimension - (progressSize), size.minDimension - (progressSize)),
            style = Stroke(width = progressSize)
        )
    }
    Box(
        modifier
            .fillMaxSize()
            .padding(progressSize.dp * 2f),
        content = content
    )
}
