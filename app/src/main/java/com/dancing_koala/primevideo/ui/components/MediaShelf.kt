package com.dancing_koala.primevideo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

sealed class Notice {
    object None : Notice()
    object IncludedWithPrime : Notice()
    object RentOrBuy : Notice()
    data class Custom(val text: String) : Notice()
}

sealed class Shelf {
    data class Small(
        val items: List<Int>,
        val title: String,
        val notice: Notice,
    ) : Shelf()

    data class Big(
        val items: List<Int>,
        val title: String = ""
    ) : Shelf()
}

@Composable
fun ShelfTitle(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

private val itemSpacing = 12.dp
private val contentPadding = 16.dp
private val cornerRadius = 4.dp

@Composable
fun MediaList(
    items: List<Int>,
    parentMaxWidth: Dp,
    ratio: Float,
    fullyVisibleItemCount: Int = 1,
) {

    val itemWidth = remember(parentMaxWidth) {
        (parentMaxWidth - (contentPadding + itemSpacing * (fullyVisibleItemCount + 1))) / fullyVisibleItemCount.toFloat()
    }
    val height = itemWidth * ratio

    val halfItemWidth = with(LocalDensity.current) {
        remember(itemWidth) { itemWidth.toPx() / 2f }
    }
    val lazyRowState = rememberLazyListState()

    val isDragged by lazyRowState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(key1 = isDragged) {
        with(lazyRowState) {
            if (!isDragged && firstVisibleItemScrollOffset != 0) {
                launch {
                    val target = if (firstVisibleItemScrollOffset > halfItemWidth) {
                        firstVisibleItemIndex + 1
                    } else {
                        firstVisibleItemIndex
                    }

                    animateScrollToItem(target)
                }
            }
        }
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        state = lazyRowState,
        contentPadding = PaddingValues(start = contentPadding, end = contentPadding),
        horizontalArrangement = Arrangement.spacedBy(itemSpacing)
    ) {
        items(items) {
            Card(
                modifier = Modifier
                    .height(height)
                    .width(itemWidth),
                shape = RoundedCornerShape(cornerRadius),
            ) {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun MediaShelf(item: Shelf) {
    when (item) {
        is Shelf.Small -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(horizontal = 16.dp)) {
                    when (val notice = item.notice) {
                        is Notice.Custom         -> CustomNotice(notice.text)
                        Notice.IncludedWithPrime -> IncludedWithPrimeNotice()
                        Notice.RentOrBuy         -> RentOrBuyNotice()
                        else                     -> Unit
                    }
                    ShelfTitle(text = item.title)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                BoxWithConstraints(Modifier.fillMaxWidth()) {
                    MediaList(
                        items = item.items,
                        parentMaxWidth = maxWidth,
                        ratio = 9f / 16f,
                        fullyVisibleItemCount = 2
                    )
                }
            }
        }
        is Shelf.Big   -> {

            Column(modifier = Modifier.fillMaxWidth()) {
                if (item.title.isNotEmpty()) {
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        ShelfTitle(text = item.title)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                BoxWithConstraints(Modifier.fillMaxWidth()) {
                    MediaList(
                        items = item.items,
                        parentMaxWidth = maxWidth,
                        ratio = 10f / 23f,
                    )
                }
            }
        }
    }
}