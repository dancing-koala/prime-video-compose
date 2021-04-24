package com.dancing_koala.primevideo.ui.components

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset

@ExperimentalAnimationApi
@Composable
fun SlideInSlideOutAnimatedVisibility(visible: Boolean, offset: IntOffset, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideIn(initialOffset = {
            offset
        }),
        exit = fadeOut() + slideOut(targetOffset = {
            offset
        }),
        content = content
    )
}