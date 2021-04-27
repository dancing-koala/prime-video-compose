package com.dancing_koala.primevideo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dancing_koala.primevideo.bottomNavigationItemModels
import com.dancing_koala.primevideo.ui.theme.PrimeBlue
import com.github.zsoltk.compose.router.Router

interface AppScreen {
    companion object {
        @Composable
        fun BottomBar(currentRoute: AppRouting, onItemClick: (AppRouting) -> Unit) {
            BottomNavigation(backgroundColor = Color.Black) {
                bottomNavigationItemModels.forEach {
                    BottomNavigationItem(
                        icon = { Icon(it.icon, contentDescription = null) },
                        label = { Text(it.label) },
                        selected = currentRoute == it.route,
                        onClick = { onItemClick(it.route) },
                        selectedContentColor = PrimeBlue,
                        unselectedContentColor = Color.White.copy(alpha = ContentAlpha.medium)
                    )
                }
            }
        }

        @OptIn(ExperimentalMaterialApi::class)
        @Composable
        fun Content() {
            Router<AppRouting>("app routing", AppRouting.Home) { backstack ->
                val currentRoute = backstack.last()

                Scaffold(
                    bottomBar = { BottomBar(currentRoute, onItemClick = { backstack.push(it) }) },
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (currentRoute) {
                            AppRouting.Home     -> HomeScreen.Content()
                            AppRouting.Channels -> ChannelsScreen.Content()
                            AppRouting.Find     -> FindScreen.Content()
                            AppRouting.MyStuff  -> MyStuffScreen.Content()
                            AppRouting.Store    -> StoreScreen.Content()
                        }
                    }
                }
            }
        }
    }

    sealed class AppRouting {
        object Home : AppRouting()
        object Store : AppRouting()
        object Channels : AppRouting()
        object Find : AppRouting()
        object MyStuff : AppRouting()
    }
}