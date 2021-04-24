package com.dancing_koala.primevideo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dancing_koala.primevideo.R
import com.dancing_koala.primevideo.ui.components.MediaCarousel
import com.dancing_koala.primevideo.ui.components.MediaShelf
import com.dancing_koala.primevideo.ui.components.Notice
import com.dancing_koala.primevideo.ui.components.Shelf
import com.dancing_koala.primevideo.ui.theme.PrimeBlack
import com.dancing_koala.primevideo.utils.makeListFullOf
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface StoreScreen {
    companion object {
        private val shelves = listOf(
            Shelf.Small(
                title = "Popular movies",
                items = makeListFullOf(R.drawable.pulp_fiction_cover_thumb, 5),
                notice = Notice.RentOrBuy
            ),
            Shelf.Small(
                title = "Golden Globe nominated and award-winning movies",
                items = makeListFullOf(R.drawable.kong_cover_thumb, 7),
                notice = Notice.RentOrBuy
            ),
            Shelf.Small(
                title = "ACADEMY AWARD nominated and award-winning movies",
                items = makeListFullOf(R.drawable.pulp_fiction_cover_thumb, 3),
                notice = Notice.RentOrBuy
            ),
            Shelf.Small(
                title = "New release movies",
                items = makeListFullOf(R.drawable.kong_cover_thumb, 6),
                notice = Notice.RentOrBuy
            ),
            Shelf.Small(
                title = "Featured movie deals",
                items = makeListFullOf(R.drawable.pulp_fiction_cover_thumb, 4),
                notice = Notice.RentOrBuy
            ),
        )

        @Composable
        fun Content() {
            var isRefreshing by remember { mutableStateOf(false) }

            LaunchedEffect(key1 = isRefreshing) {
                if (isRefreshing) {
                    launch {
                        delay(1500)
                        isRefreshing = false
                    }
                }
            }

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                onRefresh = { isRefreshing = true },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        scale = true,
                        backgroundColor = Color.White,
                        contentColor = PrimeBlack
                    )
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                ) {
                    item {
                        MediaCarousel(
                            drawable = R.drawable.kong_cover,
                            pageCount = 8
                        )
                    }

                    items(shelves) {
                        MediaShelf(it)
                    }
                }
            }
        }
    }
}