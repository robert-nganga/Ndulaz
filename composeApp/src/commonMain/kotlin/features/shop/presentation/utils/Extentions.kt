package features.shop.presentation.utils

import androidx.compose.ui.graphics.Color
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun  String.getColor(): Color {
    return when(this){
        "Black" -> Color.Black
        "White" -> Color.White
        "Red" -> Color.Red
        "Green" -> Color(0xFF558B2F)
        "Blue" -> Color(0xFF01579B)
        "Yellow" -> Color.Yellow
        "Pink" -> Color.Magenta
        else -> Color.Transparent
    }
}


fun String.getInitials(): String {
    return this.trim()
        .split(" ")
        .filter { it.isNotEmpty() }
        .take(2)
        .map { it.firstOrNull() ?: "" }
        .joinToString("")
}

fun String.getFirstName(): String {
    return this.trim()
        .split(" ")
        .firstOrNull() ?: ""

}

fun getGreetings(): String{
    val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val hours = currentTime.hour
    return when (hours) {
        in 0..11 -> "Good morning"
        in 12..15 -> "Good afternoon"
        in 16..20 -> "Good evening"
        else -> "Good night"
    }
}