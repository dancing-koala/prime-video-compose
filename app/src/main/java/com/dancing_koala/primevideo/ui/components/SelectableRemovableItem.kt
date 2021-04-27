package com.dancing_koala.primevideo.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

private enum class Slot {
    Checkbox, Content, RemoveButton
}

private enum class Swipe {
    Close, Open
}

enum class ItemMode {
    Idle, Selectable, Removable
}

private class SwipeStateHandler(var lastItemMode: ItemMode) {

    fun handleSwipe(swipeState: Swipe): ItemMode? {
        return if (swipeState == Swipe.Open && lastItemMode == ItemMode.Idle) {
            ItemMode.Removable
        } else if (swipeState == Swipe.Close && lastItemMode == ItemMode.Removable) {
            ItemMode.Idle
        } else {
            null
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectableRemovableItem(
    itemMode: ItemMode,
    onItemModeChange: (ItemMode) -> Boolean,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onRemoveClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val itemModeMutator = remember { SwipeStateHandler(itemMode) }.apply {
        lastItemMode = itemMode
    }

    val swipeableState = rememberSwipeableState(
        initialValue = Swipe.Close,
        confirmStateChange = {
            itemModeMutator.handleSwipe(it)?.let(onItemModeChange)
            true
        }
    )

    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(key1 = itemMode) {
        val job = coroutineScope.launch {
            if (itemMode == ItemMode.Removable && swipeableState.currentValue == Swipe.Close) {
                swipeableState.animateTo(Swipe.Open)
            } else if (itemMode != ItemMode.Removable && swipeableState.currentValue == Swipe.Open) {
                swipeableState.animateTo(Swipe.Close)
            }
        }

        object : DisposableEffectResult {
            override fun dispose() = job.cancel()
        }
    }

    val checkboxOffset by animateDpAsState(targetValue = if (itemMode == ItemMode.Selectable) 40.dp else 0.dp)
    val checkboxOffsetPx = with(LocalDensity.current) {
        remember(checkboxOffset) { checkboxOffset.roundToPx() }
    }
    val removableDistancePx = with(LocalDensity.current) { 72.dp.toPx() }
    val anchors = mapOf(
        -removableDistancePx to Swipe.Open,
        0f to Swipe.Close,
    )

    val removeButton = @Composable {
        IconButton(
            onClick = {},
            modifier = Modifier
                .fillMaxHeight()
                .width(72.dp)
                .background(Color.Red)
        ) {
            Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
        }
    }

    val checkbox = @Composable {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
                .width(40.dp)
                .background(MaterialTheme.colors.background)
        ) {
            Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)
        }
    }

    val modifier = Modifier.let {
        if (itemMode != ItemMode.Selectable) {
            it.swipeable(
                state = swipeableState,
                anchors = anchors,
                orientation = Orientation.Horizontal,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                resistance = ResistanceConfig(0f, 0f, 0f)
            )
        } else {
            it
        }
    }

    SubcomposeLayout(modifier = modifier) { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        layout(layoutWidth, layoutHeight) {
            val contentPlaceables = subcompose(Slot.Content, content = content).map {
                it.measure(looseConstraints)
            }

            val contentHeight = contentPlaceables.maxByOrNull { it.height }?.height ?: 0

            val adjustedConstraints = looseConstraints.copy(maxHeight = contentHeight)

            val removeButtonPlaceables = subcompose(Slot.RemoveButton, content = removeButton).map {
                it.measure(adjustedConstraints)
            }

            val checkboxPlaceables = subcompose(Slot.Checkbox, content = checkbox).map {
                it.measure(adjustedConstraints)
            }

            removeButtonPlaceables.forEach {
                it.place(layoutWidth - it.width, 0)
            }

            contentPlaceables.forEach {
                it.place(checkboxOffsetPx + swipeableState.offset.value.toInt(), 0)
            }

            checkboxPlaceables.forEach {
                val x = -it.width + checkboxOffsetPx
                it.place(x + swipeableState.offset.value.toInt(), 0)
            }
        }
    }
}
