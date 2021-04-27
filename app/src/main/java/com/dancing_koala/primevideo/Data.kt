package com.dancing_koala.primevideo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import com.dancing_koala.primevideo.ui.AppScreen
import com.dancing_koala.primevideo.ui.HomeScreen
import com.dancing_koala.primevideo.ui.MyStuffScreen

val homeTabModels: List<TabModel<HomeScreen.HomeRouting>> = listOf(
    TabModel("Home", HomeScreen.HomeRouting.HomePage),
    TabModel("TV Shows", HomeScreen.HomeRouting.TvShowsPage),
    TabModel("Movies", HomeScreen.HomeRouting.MoviesPage),
    TabModel("Kids", HomeScreen.HomeRouting.KidsPage),
    TabModel("Originals", HomeScreen.HomeRouting.OriginalsPage),
)

val bottomNavigationItemModels = listOf(
    BottomNavigationItemModel("Home", Icons.Outlined.Home, AppScreen.AppRouting.Home),
    BottomNavigationItemModel("Store", Icons.Outlined.ShoppingCart, AppScreen.AppRouting.Store),
    BottomNavigationItemModel("Channels", Icons.Outlined.List, AppScreen.AppRouting.Channels),
    BottomNavigationItemModel("Find", Icons.Outlined.Search, AppScreen.AppRouting.Find),
    BottomNavigationItemModel("My Stuff", Icons.Outlined.AccountCircle, AppScreen.AppRouting.MyStuff),
)

val myStuffTabModels: List<TabModel<MyStuffScreen.MyStuffRouting>> = listOf(
    TabModel("Downloads", MyStuffScreen.MyStuffRouting.Downloads),
    TabModel("Watchlist", MyStuffScreen.MyStuffRouting.Watchlist),
    TabModel("Purchases", MyStuffScreen.MyStuffRouting.Purchases),
)

val dropdownMenuItemModels = listOf(
    DropdownMenuItemModel("Create profile", Icons.Outlined.Add),
    DropdownMenuItemModel("Manage profiles", Icons.Outlined.Edit),
    DropdownMenuItemModel("Learn more about profiles", Icons.Outlined.Info),
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
