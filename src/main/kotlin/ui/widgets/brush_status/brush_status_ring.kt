package ui.widgets.brush_status

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun BrushStatusRing(progress: Float) {
    val color = MaterialTheme.colors.primary

    Canvas(Modifier.fillMaxSize()) {
        val arcCenter =
            center - Offset(
                size.minDimension / 2 - (progressSize / 2),
                size.minDimension / 2 - (progressSize / 2)
            )
        drawArc(
            color,
            -90f,
            360f * progress,
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
}
