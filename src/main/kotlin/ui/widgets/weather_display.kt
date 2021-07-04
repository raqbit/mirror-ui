package ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WeatherDisplay(
    modifier: Modifier = Modifier,
) {
    val degreesC = 20
    Column {
        Row {
            Text("$degreesC°")
        }
    }
}