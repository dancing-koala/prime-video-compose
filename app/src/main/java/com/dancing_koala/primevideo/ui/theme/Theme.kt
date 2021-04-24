package com.dancing_koala.primevideo.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val ColorPalette = darkColors(
    primary = PrimeGray,
    primaryVariant = PrimeDarkestGray,
    secondary = PrimeBlue,
    surface = PrimeDarkGray,
    onSurface = Color.White,
    background = PrimeDarkGray,
    onBackground = Color.White,
    onPrimary = Color.White,
)

@Composable
fun PrimeVideoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = ColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

object Dimensions {
    val basePadding = 24.dp
}