package com.dancing_koala.primevideo.ui.components

import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dancing_koala.primevideo.ui.theme.PrimeBlue

@Composable
fun IncludedWithPrimeNotice() {
    Text(
        text = "Included with Prime",
        color = PrimeBlue,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CustomNotice(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.alpha(ContentAlpha.medium)
    )
}

@Composable
fun RentOrBuyNotice() {
    CustomNotice(text = "Rent or Buy")
}
