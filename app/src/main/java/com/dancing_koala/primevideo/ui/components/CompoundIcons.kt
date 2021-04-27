package com.dancing_koala.primevideo.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dancing_koala.primevideo.ui.theme.PrimeBlack50

@Composable
fun PlayIcon(modifier: Modifier = Modifier) {
    Surface(
        color = PrimeBlack50,
        shape = CircleShape,
        border = BorderStroke(1.dp, Color.White),
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = "",
            modifier = Modifier.padding(4.dp)
        )
    }
}
