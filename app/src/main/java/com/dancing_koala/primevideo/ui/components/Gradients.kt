package com.dancing_koala.primevideo.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.dancing_koala.primevideo.ui.theme.PrimeBlueAfternoon60
import com.dancing_koala.primevideo.ui.theme.PrimeDarkestGray
import com.dancing_koala.primevideo.ui.theme.PrimePurpleNocturnal70
import com.dancing_koala.primevideo.ui.theme.PrimerBlueSeaActive30

@Composable
fun GradientsBackgroundCanvas(
    drawBlue: Boolean = false,
    drawPurple: Boolean = false
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        if (drawBlue) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(PrimeBlueAfternoon60, PrimerBlueSeaActive30, PrimeDarkestGray),
                    center = Offset.Zero,
                ),
                center = Offset.Zero,
                radius = size.width * 0.66f
            )
        }

        if (drawPurple) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(PrimePurpleNocturnal70, Color.Transparent),
                    center = Offset.Zero.copy(x = size.width),
                ),
                center = Offset.Zero.copy(x = size.width),
                radius = size.width * 0.66f
            )
        }
    }
}
