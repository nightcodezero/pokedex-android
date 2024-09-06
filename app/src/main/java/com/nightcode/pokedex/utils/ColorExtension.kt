package com.nightcode.pokedex.utils

import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt

fun Color.toHexString(): String {
    // Extracting the ARGB components
    val alpha = (alpha * 255).roundToInt()
    val red = (red * 255).roundToInt()
    val green = (green * 255).roundToInt()
    val blue = (blue * 255).roundToInt()

    // Formatting to #AARRGGBB format
    return "#%02X%02X%02X%02X".format(alpha, red, green, blue)
}