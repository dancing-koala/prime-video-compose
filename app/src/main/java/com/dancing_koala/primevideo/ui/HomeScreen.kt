package com.dancing_koala.primevideo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dancing_koala.primevideo.R
import com.dancing_koala.primevideo.ui.theme.PrimeBlack
import com.dancing_koala.primevideo.ui.theme.PrimeDarkGray
import com.github.zsoltk.compose.router.Router

interface HomeScreen {
    companion object {

        private val tabModels = listOf(
            TabModel("Home", HomeRouting.HomePage),
            TabModel("TV Shows", HomeRouting.TvShowsPage),
            TabModel("Movies", HomeRouting.MoviesPage),
            TabModel("Kids", HomeRouting.KidsPage),
            TabModel("Originals", HomeRouting.OriginalsPage),
        )

        private val baseTopBarHeight = 56.dp

        @Composable
        fun Content() {
            var scrollOffsetY by remember { mutableStateOf(0f) }
            val minOffsetValue = with(LocalDensity.current) {
                remember { -baseTopBarHeight.toPx() }
            }
            val topBarHeight = with(LocalDensity.current) {
                (baseTopBarHeight + scrollOffsetY.toDp())
            }

            val nestedScrollConnection = remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                        if (scrollOffsetY + available.y < minOffsetValue) {
                            val diff = minOffsetValue - scrollOffsetY

                            return if (diff < 0) {
                                scrollOffsetY += diff
                                Offset.Zero.copy(y = diff)
                            } else {
                                Offset.Zero
                            }
                        }

                        if (scrollOffsetY + available.y > 0) {
                            val diff = -scrollOffsetY

                            return if (diff > 0) {
                                scrollOffsetY += diff
                                Offset.Zero.copy(y = diff)
                            } else {
                                Offset.Zero
                            }
                        }

                        scrollOffsetY += available.y
                        return available.copy(x = 0f)
                    }
                }
            }

            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    Modifier
                        .height(topBarHeight)
                        .fillMaxWidth()
                        .background(PrimeDarkGray)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier
                            .width(72.dp)
                            .height(baseTopBarHeight),
                        painter = painterResource(id = R.drawable.logo_white),
                        contentDescription = ""
                    )
                }
                Router<HomeRouting>("home routing", defaultRouting = HomeRouting.HomePage) { backStack ->
                    val currentRoute = backStack.last()

                    Column(modifier = Modifier.fillMaxSize()) {
                        TabRow(selectedTabIndex = tabModels.indexOfFirst { it.route == currentRoute }) {
                            tabModels.forEach {
                                Tab(
                                    text = { Text(text = it.label, fontSize = 10.sp) },
                                    selected = currentRoute == it.route,
                                    onClick = { backStack.replace(it.route) }
                                )
                            }
                        }

                        when (currentRoute) {
                            HomeRouting.HomePage      -> HomePage.Content(nestedScrollConnection)
                            HomeRouting.KidsPage      -> Text("Kids page")
                            HomeRouting.MoviesPage    -> Text("Movies page")
                            HomeRouting.OriginalsPage -> Text("Originals page")
                            HomeRouting.TvShowsPage   -> Text("Tv Shows page")
                        }
                    }
                }
            }
        }
    }

    data class TabModel(val label: String, val route: HomeRouting)

    sealed class HomeRouting {
        object HomePage : HomeRouting()
        object TvShowsPage : HomeRouting()
        object MoviesPage : HomeRouting()
        object KidsPage : HomeRouting()
        object OriginalsPage : HomeRouting()
    }
}