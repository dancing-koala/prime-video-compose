package com.dancing_koala.primevideo.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dancing_koala.primevideo.TabModel
import com.dancing_koala.primevideo.dropdownMenuItemModels
import com.dancing_koala.primevideo.myStuffTabModels
import com.dancing_koala.primevideo.ui.components.GradientsBackgroundCanvas
import com.dancing_koala.primevideo.ui.theme.Dimensions
import com.dancing_koala.primevideo.ui.theme.PrimeWhite50
import com.dancing_koala.primevideo.ui.theme.Shapes
import com.github.zsoltk.compose.router.Router

interface MyStuffScreen {
    companion object {
        @Composable
        fun TopBar() {
            Box(modifier = Modifier.fillMaxWidth()) {
                var menuExpanded by remember { mutableStateOf(false) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimensions.basePadding, vertical = 20.dp)
                        .clickable { menuExpanded = true }
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "",
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.size(Dimensions.basePadding))

                    Text(
                        text = "User Name",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    val arrowRotation by animateFloatAsState(
                        targetValue = if (menuExpanded) -180f else 0f
                    )

                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.dp)
                            .alpha(ContentAlpha.medium)
                            .rotate(arrowRotation)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "",
                            modifier = Modifier.alpha(ContentAlpha.medium)
                        )
                    }
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(1.dp, PrimeWhite50), shape = Shapes.small)
                        .background(MaterialTheme.colors.background)
                ) {
                    dropdownMenuItemModels.forEach {
                        DropdownMenuItem(
                            onClick = { menuExpanded = false },
                        ) {
                            Icon(it.icon, "")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(it.label)
                        }
                    }
                }
            }
        }

        @Composable
        fun Tabs(tabModels: List<TabModel<MyStuffRouting>>, selectedTabIndex: Int, onItemClick: (Int) -> Unit) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                backgroundColor = Color.Transparent,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .padding(horizontal = 16.dp)
                    )
                }
            ) {
                tabModels.forEachIndexed { index, tabModel ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = { onItemClick(index) },
                        modifier = Modifier.padding(vertical = 16.dp)
                    ) {
                        Text(
                            text = tabModel.label,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        @Composable
        fun Content() {
            Box(modifier = Modifier.fillMaxSize()) {
                GradientsBackgroundCanvas(drawBlue = true)

                Column(modifier = Modifier.fillMaxWidth()) {
                    TopBar()
                    Router<MyStuffRouting>("my stuff router", MyStuffRouting.Downloads) { backstack ->
                        val currentRoute = backstack.last()

                        val selectedTabIndex = myStuffTabModels.indexOfFirst { it.route == currentRoute }

                        Tabs(
                            tabModels = myStuffTabModels,
                            selectedTabIndex = selectedTabIndex,
                            onItemClick = { backstack.push(myStuffTabModels[it].route) }
                        )

                        Box(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            when (currentRoute) {
                                MyStuffRouting.Downloads -> DownloadsPageScreen.Content()
                                MyStuffRouting.Purchases -> Text("Purchases")
                                MyStuffRouting.Watchlist -> Text("Watchlist")
                            }
                        }
                    }
                }
            }
        }
    }


    sealed class MyStuffRouting {
        object Downloads : MyStuffRouting()
        object Watchlist : MyStuffRouting()
        object Purchases : MyStuffRouting()
    }
}