package com.dancing_koala.primevideo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.dancing_koala.primevideo.R
import com.dancing_koala.primevideo.ui.components.MediaCarousel
import com.dancing_koala.primevideo.ui.components.MediaShelf
import com.dancing_koala.primevideo.ui.components.Notice
import com.dancing_koala.primevideo.ui.components.Shelf
import com.dancing_koala.primevideo.ui.theme.Dimensions
import com.dancing_koala.primevideo.ui.theme.PrimeBlack
import com.dancing_koala.primevideo.utils.makeListFullOf
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

interface HomePageScreen {
    companion object {

        private val shelves = listOf(
            Shelf.Small(
                items = makeListFullOf(R.drawable.parks_recs_cover, 5),
                title = "Amazon Originals and Exclusives",
                notice = Notice.IncludedWithPrime
            ),
            Shelf.Big(
                items = listOf(
                    R.drawable.prime_ad_1,
                    R.drawable.prime_ad_2,
                    R.drawable.prime_ad_3,
                    R.drawable.prime_ad_4,
                    R.drawable.prime_ad_5,
                )
            ),
            Shelf.Small(
                items = makeListFullOf(R.drawable.pulp_fiction_cover, 7),
                title = "Watch next TV and movies",
                notice = Notice.None
            ),
            Shelf.Small(
                items = makeListFullOf(R.drawable.parks_recs_cover, 3),
                title = "Movies we think you'll like",
                notice = Notice.IncludedWithPrime
            ),
            Shelf.Small(
                items = makeListFullOf(R.drawable.parks_recs_cover, 6),
                title = "New release movies",
                notice = Notice.RentOrBuy
            ),
            Shelf.Small(
                items = makeListFullOf(R.drawable.pulp_fiction_cover, 4),
                title = "Binge-worthy box sets",
                notice = Notice.IncludedWithPrime
            ),
        )

        @Composable
        fun Content(nestedScrollConnection: NestedScrollConnection) {
            var isRefreshing by remember { mutableStateOf(false) }

            LaunchedEffect(key1 = isRefreshing) {
                if (isRefreshing) {
                    delay(1500)
                    isRefreshing = false
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
                val lazyColumnState = rememberLazyListState()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(nestedScrollConnection),
                    state = lazyColumnState,
                    verticalArrangement = Arrangement.spacedBy(Dimensions.basePadding),
                    contentPadding = PaddingValues(bottom = 16.dp),
                ) {
                    item {
                        MediaCarousel(
                            drawable = R.drawable.pulp_fiction_cover,
                            pageCount = 4
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