package com.dancing_koala.primevideo.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dancing_koala.primevideo.ui.theme.PrimeBlue

@Composable
fun ProgressBar(progress: Float, thickness: Dp = 4.dp) {
    Divider(
        color = Color.White,
        thickness = thickness,
        modifier = Modifier.alpha(.5f),
    )
    Divider(
        color = PrimeBlue,
        thickness = thickness,
        modifier = Modifier.fillMaxWidth(progress)
    )
}
