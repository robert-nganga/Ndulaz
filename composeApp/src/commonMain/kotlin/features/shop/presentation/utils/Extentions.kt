package features.shop.presentation.utils

import androidx.compose.ui.graphics.Color

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