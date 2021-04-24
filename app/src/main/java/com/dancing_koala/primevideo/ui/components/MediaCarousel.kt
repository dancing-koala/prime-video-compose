package com.dancing_koala.primevideo.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MediaCarousel(@DrawableRes drawable: Int, pageCount: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(192.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        val pagerState = rememberPagerState(pageCount = pageCount)

        LaunchedEffect(key1 = pagerState.currentPage) {
            launch {
                delay(3000)
                with(pagerState) {
                    val target = if (currentPage < pageCount - 1) {
                        currentPage + 1
                    } else {
                        0
                    }

                    animateScrollToPage(
                        page = target,
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = drawable),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        HorizontalPagerIndicator(
            modifier = Modifier.padding(bottom = 8.dp),
            pagerState = pagerState
        )
    }
}
