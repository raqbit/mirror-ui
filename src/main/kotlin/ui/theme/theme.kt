package ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

const val BLUE_RIPPLE = 0x82a5ff

@Immutable
private object MirrorRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color {
        return Color(BLUE_RIPPLE)
    }

    @Composable
    override fun rippleAlpha() = RippleAlpha(
        pressedAlpha = 0.6f,
        focusedAlpha = 0f,
        draggedAlpha = 0f,
        hoveredAlpha = 0f
    )
}

@Composable
fun MirrorTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            background = Color.Black,
            surface = Color.Black,
            primary = Color.White,
            onBackground = Color.White,
            onSurface = Color.White,
            isLight = false,
        ),
        typography = MaterialTheme.typography.copy(
            body1 = TextStyle.Default.copy(Color.White)
        )
    ) {
        CompositionLocalProvider(
            LocalRippleTheme provides MirrorRippleTheme,
            content = content,
        )
    }
}