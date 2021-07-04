package ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatterBuilder
import java.time.format.TextStyle
import java.time.temporal.ChronoField
import java.util.*

val timeFormatter = DateTimeFormatterBuilder().appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral(':').appendValue(ChronoField.MINUTE_OF_HOUR, 2).toFormatter()!!

@Composable
fun TimeDateDisplay(
    modifier: Modifier = Modifier,
    locale: Locale = Locale.ENGLISH,
) {
    var now by remember { mutableStateOf(LocalDateTime.now()) }

    LaunchedEffect(true) {
        timer().distinctUntilChanged { old, new ->
            old.minute == new.minute
        }.onEach {
            now = it
        }.collect()
    }

    Column(modifier) {
        Text(formatTime(now.toLocalTime()), fontSize = 200.sp)
        Text("${formatDayOfWeek(now.dayOfWeek, locale)}, ${formatDate(now.toLocalDate(), locale)}", fontSize = 60.sp)
    }
}

fun timer() = flow<LocalDateTime> {
    while (true) {
        delay(1000)
        emit(LocalDateTime.now())
    }
}

fun formatDayOfWeek(dayOfWeek: DayOfWeek, locale: Locale): String {
    return dayOfWeek.getDisplayName(TextStyle.FULL, locale)
}

fun formatDate(date: LocalDate, locale: Locale): String {
    return "${date.month.getDisplayName(TextStyle.FULL, locale)} ${date.dayOfMonth}"
}
fun formatTime(time: LocalTime): String {
    return time.format(timeFormatter)
}