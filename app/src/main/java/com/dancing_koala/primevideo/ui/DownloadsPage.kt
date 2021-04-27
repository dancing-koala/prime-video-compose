package com.dancing_koala.primevideo.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.dancing_koala.primevideo.DownloadItem
import com.dancing_koala.primevideo.DownloadItemType
import com.dancing_koala.primevideo.downloadItems
import com.dancing_koala.primevideo.downloadsMetadata
import com.dancing_koala.primevideo.ui.components.*
import com.dancing_koala.primevideo.ui.theme.Dimensions
import com.dancing_koala.primevideo.ui.theme.PrimeBlue
import com.dancing_koala.primevideo.ui.theme.PrimeGray
import com.dancing_koala.primevideo.ui.theme.PrimeWhite50

interface DownloadsPage {
    companion object {

        private val itemHeight = 96.dp

        @Composable
        fun DownloadItemLayout(
            item: DownloadItem,
            itemMode: ItemMode,
            onItemModeChange: (ItemMode) -> Boolean,
            isSelected: Boolean,
            onSelectionChange: (Boolean) -> Unit,
            onLongPress: () -> Unit,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(itemHeight)
                    .background(PrimeGray)
            ) {
                SelectableRemovableItem(
                    itemMode = itemMode,
                    onItemModeChange = onItemModeChange,
                    isSelected = isSelected,
                    onSelectionChange = onSelectionChange,
                    onRemoveClick = {},
                ) {
                    val modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(PrimeGray)
                        .pointerInput(key1 = itemMode, key2 = isSelected) {
                            detectTapGestures(
                                onLongPress = { onLongPress() },
                                onTap = {
                                    when (itemMode) {
                                        ItemMode.Idle       -> Unit
                                        ItemMode.Selectable -> onSelectionChange(!isSelected)
                                        ItemMode.Removable  -> onItemModeChange(ItemMode.Idle)
                                    }
                                }
                            )
                        }.let {
                            if (isSelected) it.alpha(.5f) else it
                        }

                    Row(modifier = modifier) {
                        Box(
                            contentAlignment = Alignment.BottomStart,
                            modifier = Modifier
                                .weight(0.4f, true)
                                .fillMaxHeight()
                        ) {
                            Image(
                                painter = painterResource(id = item.image),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                            )

                            if (item.type is DownloadItemType.Playable) {
                                PlayIcon(Modifier.padding(16.dp))

                                if (item.type.progress > 0f) {
                                    ProgressBar(progress = item.type.progress)
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
                                    is DownloadItemType.Group    -> "${item.type.itemCount} episodes"
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
                var editingModeEnabled by remember { mutableStateOf(false) }

                var selectAll by remember { mutableStateOf(false) }

                var checkedItems by remember { mutableStateOf(downloadItems.map { false }) }

                var itemModes by remember { mutableStateOf(downloadItems.map { ItemMode.Idle }) }

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
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
                            isSelected = checkedItems[itemIndex],
                            onSelectionChange = { isChecked ->
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

                AnimatedVisibility(visible = selectAll) {
                    TextButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(Color.Red)
                    ) {
                        Text(text = "Delete (33)", color = Color.White)
                    }

                }
            }
        }
    }
}