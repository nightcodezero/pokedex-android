package com.nightcode.pokedex.utils

import androidx.compose.ui.graphics.Color

fun String.toColor(): Color {
    val colorInt =
        when (length) {
            9 -> android.graphics.Color.parseColor(this) // #AARRGGBB format
            7 -> android.graphics.Color.parseColor("#FF" + substring(1)) // #RRGGBB format, adds full opacity
            else -> throw IllegalArgumentException("Invalid color format")
        }

    // Create Color using ARGB values
    return Color(colorInt)
}