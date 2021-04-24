package com.dancing_koala.primevideo.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlin.math.max
import kotlin.math.min

@Composable
fun SimpleGridLayout(
    modifier: Modifier = Modifier,
    itemsPerLine: Int,
    itemSpacing: Dp,
    content: @Composable () -> Unit
) {
    val itemSpacingValue = with(LocalDensity.current) {
        remember(itemSpacing) { itemSpacing.toPx().toInt() }
    }

    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val itemWidth = (constraints.maxWidth - ((itemsPerLine - 1) * itemSpacingValue)) / itemsPerLine

        val lineCount = if (measurables.size % itemsPerLine == 0) {
            measurables.size / itemsPerLine
        } else {
            (measurables.size / itemsPerLine) + 1
        }

        val placeables = measurables.map { measurable ->
            measurable.measure(constraints.copy(maxWidth = itemWidth, minHeight = 0, minWidth = itemWidth))
        }

        val arranged = mutableListOf<List<Placeable>>().apply {
            var index = 0

            repeat(lineCount) {
                val line = mutableListOf<Placeable>().apply {
                    val diff = placeables.size - index

                    repeat(min(itemsPerLine, diff)) {
                        add(placeables[index])
                        index++
                    }
                }

                add(line)
            }
        }

        val layoutHeight = arranged.sumBy {
            it.maxByOrNull { placeable -> placeable.height }?.height ?: 0
        } + (lineCount - 1) * itemSpacingValue

        layout(constraints.maxWidth, layoutHeight) {
            var yPosition = 0

            arranged.forEach { line ->
                var xPosition = 0
                var maxHeight = 0

                line.forEach { placeable ->
                    placeable.place(x = xPosition, y = yPosition)
                    maxHeight = max(maxHeight, placeable.height)
                    xPosition += placeable.width + itemSpacingValue
                }

                yPosition += maxHeight + itemSpacingValue
            }
        }
    }
}
