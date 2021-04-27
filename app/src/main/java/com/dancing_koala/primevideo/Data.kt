package com.dancing_koala.primevideo

import androidx.annotation.DrawableRes

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

val downloadItems = listOf(
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

val downloadsMetadata = listOf("12 videos", "•", "5h 33min", "•", "1.5 GB")
