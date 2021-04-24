package com.dancing_koala.primevideo.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dancing_koala.primevideo.R
import com.dancing_koala.primevideo.ui.components.GradientsBackgroundCanvas
import com.dancing_koala.primevideo.ui.components.SlideInSlideOutAnimatedVisibility
import com.dancing_koala.primevideo.ui.theme.*
import com.github.zsoltk.compose.router.Router

interface MyStuffScreen {
    companion object {
        private val tabModels = listOf(
            TabModel("Downloads", MyStuffRouting.Downloads),
            TabModel("Watchlist", MyStuffRouting.Watchlist),
            TabModel("Purchases", MyStuffRouting.Purchases),
        )

        private val dropdownMenuItemModels = listOf(
            DropdownMenuItemModel("Create profile", Icons.Outlined.Add),
            DropdownMenuItemModel("Manage profiles", Icons.Outlined.Edit),
            DropdownMenuItemModel("Learn more about profiles", Icons.Outlined.Info),
        )

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
        fun Tabs(tabModels: List<TabModel>, selectedTabIndex: Int, onItemClick: (Int) -> Unit) {
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

        private val downloadItems = listOf(
            DownloadItem(
                name = "Parks And Recreation",
                image = R.drawable.parks_recs_cover,
                size = "983 MB",
                type = DownloadItemType.Group(10)
            ),
            DownloadItem(
                name = "Godzilla VS Kong",
                image = R.drawable.kong_cover_thumb,
                size = "576 MB",
                type = DownloadItemType.Playable("119 min", 0.4f)
            ),
            DownloadItem(
                name = "Game of Thrones",
                image = R.drawable.got_cover_thumb,
                size = "1.5 GB",
                type = DownloadItemType.Group(12)
            ),
            DownloadItem(
                name = "Pulp Fiction",
                image = R.drawable.pulp_fiction_cover_thumb,
                size = "876 MB",
                type = DownloadItemType.Playable("124 min", 0.75f)
            ),
        )

        private val downloadsMetadata = listOf("12 videos", "•", "5h 33min", "•", "1.5 GB")

        @Composable
        fun DownloadItemLayout(
            item: DownloadItem,
            downloadItemsOffset: Dp,
            isChecked: Boolean,
            onCheckedChange: (Boolean) -> Unit,
            onLongPress: () -> Unit,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .offset(x = downloadItemsOffset)
                    .pointerInput(Unit) {
                        detectTapGestures(onLongPress = { onLongPress() })
                    }
                    .background(PrimeGray)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(40.dp)
                        .fillMaxHeight()
                        .offset(x = (-40).dp)
                        .background(PrimeGray)
                ) {
                    Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        contentAlignment = Alignment.BottomStart,
                        modifier = Modifier.weight(0.4f, true)
                    ) {
                        Image(
                            painter = painterResource(id = item.image),
                            contentDescription = "",
                            contentScale = ContentScale.FillWidth,
                        )

                        if (item.type is DownloadItemType.Playable) {
                            Surface(
                                color = PrimeBlack50,
                                shape = CircleShape,
                                border = BorderStroke(1.dp, Color.White),
                                modifier = Modifier.padding(16.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.PlayArrow,
                                    contentDescription = "",
                                    modifier = Modifier.padding(4.dp)
                                )
                            }

                            if (item.type.progress > 0f) {
                                Divider(
                                    color = Color.White,
                                    thickness = 4.dp,
                                    modifier = Modifier.alpha(.5f),
                                )
                                Divider(
                                    color = PrimeBlue,
                                    thickness = 4.dp,
                                    modifier = Modifier.fillMaxWidth(item.type.progress)
                                )
                            }
                        }
                    }
                    Box(
                        contentAlignment = Alignment.BottomEnd,
                        modifier = Modifier
                            .weight(0.6f, true)
                            .fillMaxHeight()
                            .padding(8.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = item.name,
                                color = Color.White,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            val detail = when (item.type) {
                                is DownloadItemType.Group -> "${item.type.itemCount} episodes"
                                is DownloadItemType.Playable -> item.type.duration
                            }
                            Text(
                                text = "$detail  ${item.size}",
                                color = PrimeWhite50,
                                fontSize = 13.sp,
                            )
                            Text(
                                text = "prime",
                                color = PrimeBlue,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            val icon = if (item.type is DownloadItemType.Group) {
                                Icons.Outlined.KeyboardArrowRight
                            } else {
                                Icons.Outlined.MoreVert
                            }

                            Icon(
                                imageVector = icon,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(32.dp)
                                    .alpha(.5f)
                            )
                        }
                    }
                }
            }
        }

        @Composable
        fun Footer() {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimensions.basePadding)
            ) {
                Text(
                    text = "Auto Downloads:",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = Dimensions.basePadding),
                )

                Text(
                    text = "On",
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = Dimensions.basePadding),
                )

                Text(
                    text = "•",
                    fontSize = 14.sp,
                    color = PrimeWhite50,
                )

                TextButton(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.textButtonColors(contentColor = PrimeBlue)
                ) {
                    Text("Manage")
                }
            }
        }

        @OptIn(ExperimentalAnimationApi::class)
        @Composable
        fun DownloadsPage() {
            Column(modifier = Modifier.fillMaxWidth()) {
                var editingModeOn by remember {
                    mutableStateOf(false)
                }
                var selectAll by remember {
                    mutableStateOf(false)
                }

                val targetOffset = with(LocalDensity.current) {
                    IntOffset(40.dp.toPx().toInt(), 0)
                }

                var checkedItems by remember {
                    mutableStateOf(downloadItems.map { false })
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(end = Dimensions.basePadding)
                ) {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        SlideInSlideOutAnimatedVisibility(
                            visible = editingModeOn,
                            offset = IntOffset.Zero - targetOffset
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .clickable {
                                        selectAll = !selectAll
                                        checkedItems = checkedItems.map { selectAll }
                                    }
                            ) {
                                Checkbox(
                                    checked = selectAll,
                                    onCheckedChange = {
                                        selectAll = it
                                        checkedItems = checkedItems.map { selectAll }
                                    },
                                )
                                Text(
                                    text = if (selectAll) "Unselect all" else "Select all",
                                    fontSize = 14.sp,
                                    color = PrimeWhite50,
                                )
                            }
                        }

                        SlideInSlideOutAnimatedVisibility(
                            visible = !editingModeOn,
                            offset = targetOffset
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(start = Dimensions.basePadding)
                            ) {
                                downloadsMetadata.forEach {
                                    Text(
                                        text = it,
                                        fontSize = 14.sp,
                                        color = PrimeWhite50,
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(onClick = {
                        editingModeOn = !editingModeOn
                        if (!editingModeOn) {
                            selectAll = false
                            checkedItems = checkedItems.map { false }
                        }
                    }) {
                        Text(if (editingModeOn) "Cancel" else "Edit")
                    }
                }

                val downloadItemsOffset by animateDpAsState(targetValue = if (editingModeOn) 40.dp else 0.dp)

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(downloadItems) { index, item ->

                        DownloadItemLayout(
                            item = item,
                            downloadItemsOffset = downloadItemsOffset,
                            isChecked = checkedItems[index],
                            onCheckedChange = { isChecked ->
                                checkedItems = checkedItems.toMutableList().apply {
                                    set(index, isChecked)
                                    if (isChecked && !selectAll) {
                                        selectAll = true
                                    }
                                }
                            },
                            onLongPress = {
                                if (!editingModeOn) {
                                    editingModeOn = true

                                    checkedItems = checkedItems.toMutableList().apply {
                                        set(index, true)
                                        selectAll = true
                                    }
                                }
                            }
                        )
                    }

                    item { Footer() }
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

                        val selectedTabIndex = tabModels.indexOfFirst { it.route == currentRoute }

                        Tabs(
                            tabModels = tabModels,
                            selectedTabIndex = selectedTabIndex,
                            onItemClick = { backstack.push(tabModels[it].route) }
                        )

                        Box(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            when (currentRoute) {
                                MyStuffRouting.Downloads -> DownloadsPage()
                                MyStuffRouting.Purchases -> Text("Purchases")
                                MyStuffRouting.Watchlist -> Text("Watchlist")
                            }
                        }
                    }
                }
            }
        }
    }

    data class TabModel(val label: String, val route: MyStuffRouting)

    data class DropdownMenuItemModel(val label: String, val icon: ImageVector)

    sealed class DownloadItemType {
        class Group(val itemCount: Int) : DownloadItemType()
        class Playable(val duration: String, val progress: Float) : DownloadItemType()
    }

    data class DownloadItem(
        val name: String,
        @DrawableRes val image: Int,
        val size: String,
        val type: DownloadItemType
    )

    sealed class MyStuffRouting {
        object Downloads : MyStuffRouting()
        object Watchlist : MyStuffRouting()
        object Purchases : MyStuffRouting()
    }
}