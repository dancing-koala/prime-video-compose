package com.dancing_koala.primevideo

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.dancing_koala.primevideo.ui.AppScreen

data class TabModel<T>(val label: String, val route: T)

data class BottomNavigationItemModel(
    val label: String,
    val icon: ImageVector,
    val route: AppScreen.AppRouting
)

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

data class DropdownMenuItemModel(val label: String, val icon: ImageVector)
