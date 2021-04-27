package com.dancing_koala.primevideo.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dancing_koala.primevideo.R
import com.dancing_koala.primevideo.ui.components.ItemMode
import com.dancing_koala.primevideo.ui.components.SelectableRemovableItem
import com.dancing_koala.primevideo.ui.components.SlideInSlideOutAnimatedVisibility
import com.dancing_koala.primevideo.ui.theme.*

interface DownloadsPage {
    companion object {

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

        @OptIn(ExperimentalMaterialApi::class)
        @Composable
        fun DownloadItemLayout(
            item: DownloadItem,
            itemMode: ItemMode,
            onItemModeChange: (ItemMode) -> Boolean,
            isChecked: Boolean,
            onCheckedChange: (Boolean) -> Unit,
            onLongPress: () -> Unit,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .pointerInput(Unit) { detectTapGestures(onLongPress = { onLongPress() }) }
                    .background(PrimeGray)
            ) {
                SelectableRemovableItem(
                    itemMode = itemMode,
                    onItemModeChange = onItemModeChange,
                    isChecked = isChecked,
                    onCheckedChange = onCheckedChange,
                    onRemoveClick = {}
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(PrimeGray)
                    ) {
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
        fun Toolbar(
            editingModeEnabled: Boolean,
            onEditingModeToggle: (Boolean) -> Unit,
            selectAll: Boolean,
            onSelectAllToggle: (Boolean) -> Unit,
        ) {
            val targetOffset = with(LocalDensity.current) {
                remember { IntOffset(40.dp.toPx().toInt(), 0) }
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
                        visible = editingModeEnabled,
                        offset = IntOffset.Zero - targetOffset
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable { onSelectAllToggle(!selectAll) }
                        ) {
                            Checkbox(
                                checked = selectAll,
                                onCheckedChange = onSelectAllToggle,
                            )
                            Text(
                                text = if (selectAll) "Unselect all" else "Select all",
                                fontSize = 14.sp,
                                color = PrimeWhite50,
                            )
                        }
                    }

                    SlideInSlideOutAnimatedVisibility(
                        visible = !editingModeEnabled,
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
                    onEditingModeToggle(!editingModeEnabled)
                }) {
                    Text(if (editingModeEnabled) "Cancel" else "Edit")
                }
            }
        }

        @OptIn(ExperimentalAnimationApi::class)
        @Composable
        fun Content() {
            Column(modifier = Modifier.fillMaxWidth()) {
                var editingModeEnabled by remember {
                    mutableStateOf(false)
                }
                var selectAll by remember {
                    mutableStateOf(false)
                }

                var checkedItems by remember {
                    mutableStateOf(downloadItems.map { false })
                }

                var itemModes by remember {
                    mutableStateOf(downloadItems.map { ItemMode.Idle })
                }

                Toolbar(
                    editingModeEnabled = editingModeEnabled,
                    onEditingModeToggle = {
                        editingModeEnabled = it

                        itemModes = itemModes.map {
                            if (editingModeEnabled) {
                                ItemMode.Selectable
                            } else {
                                ItemMode.Idle
                            }
                        }

                        if (!editingModeEnabled) {
                            selectAll = false
                            checkedItems = checkedItems.map { false }
                        }
                    },
                    selectAll = selectAll,
                    onSelectAllToggle = {
                        selectAll = it
                        checkedItems = checkedItems.map { selectAll }
                    }
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(downloadItems) { itemIndex, item ->
                        DownloadItemLayout(
                            item = item,
                            itemMode = itemModes[itemIndex],
                            onItemModeChange = { newItemMode ->
                                itemModes = itemModes.mapIndexed { index, itemMode ->
                                    if (itemIndex == index) {
                                        newItemMode
                                    } else {
                                        itemMode
                                    }
                                }
                                true
                            },
                            isChecked = checkedItems[itemIndex],
                            onCheckedChange = { isChecked ->
                                checkedItems = checkedItems.toMutableList().apply {
                                    set(itemIndex, isChecked)
                                }
                                selectAll = checkedItems.any { it }
                            },
                            onLongPress = {
                                if (!editingModeEnabled) {
                                    editingModeEnabled = true
                                    selectAll = true
                                    itemModes = itemModes.map { ItemMode.Selectable }
                                    checkedItems = checkedItems.mapIndexed { index, value ->
                                        index == itemIndex || value
                                    }
                                }
                            }
                        )
                    }

                    item { Footer() }
                }
            }
        }
    }

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
}