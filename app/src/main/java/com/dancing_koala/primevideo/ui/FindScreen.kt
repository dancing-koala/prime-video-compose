package com.dancing_koala.primevideo.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dancing_koala.primevideo.ui.components.GradientsBackgroundCanvas
import com.dancing_koala.primevideo.ui.components.SimpleGridLayout
import com.dancing_koala.primevideo.ui.theme.PrimeBlue
import com.dancing_koala.primevideo.ui.theme.PrimeGray
import com.dancing_koala.primevideo.ui.theme.PrimeWhite50

interface FindScreen {
    companion object {

        @Composable
        fun FindSectionTitle(text: String) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        @Composable
        fun SearchBox() {
            Card(
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(1.dp, PrimeWhite50),
                backgroundColor = MaterialTheme.colors.background,
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "",
                        tint = PrimeWhite50
                    )
                    Text(
                        text = "Search by actor, title...",
                        textAlign = TextAlign.Center,
                        color = PrimeWhite50,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        @Composable
        fun CategoryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = PrimeGray),
                contentPadding = PaddingValues(4.dp),
                onClick = onClick,
                modifier = modifier
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        @Composable
        fun BrowseBySection() {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                FindSectionTitle("Browse by")

                SimpleGridLayout(
                    itemSpacing = 8.dp,
                    itemsPerLine = 2,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CategoryButton(
                        text = "TV",
                        onClick = { /*TODO*/ },
                        modifier = Modifier.fillMaxWidth()
                    )
                    CategoryButton(
                        text = "Movies",
                        onClick = { /*TODO*/ },
                        modifier = Modifier.fillMaxWidth()
                    )
                    CategoryButton(
                        text = "Amazon Originals",
                        onClick = { /*TODO*/ },
                        modifier = Modifier.fillMaxWidth()
                    )
                    CategoryButton(
                        text = "Kids",
                        onClick = { /*TODO*/ },
                        modifier = Modifier.fillMaxWidth()
                    )
                    CategoryButton(
                        text = "Made in Europe",
                        onClick = { /*TODO*/ },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        private val genreButtonContentPadding = PaddingValues(
            start = 0.dp,
            top = ButtonDefaults.ContentPadding.calculateTopPadding(),
            end = 0.dp,
            bottom = ButtonDefaults.ContentPadding.calculateBottomPadding()
        )

        private val genres = listOf(
            "Action and adventure",
            "Comedy",
            "Drama",
            "Documentary",
            "Kids and family",
            "Fantasy",
            "Horror",
            "Romance",
            "Science Fiction",
            "Thriller",
            "Anime",
        )

        @Composable
        fun GenreDivider() {
            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.alpha(.2f)
            )
        }

        @Composable
        fun GenreItem(
            textColor: Color = Color.White,
            name: String,
            onClick: () -> Unit,
        ) {
            TextButton(
                onClick = onClick,
                contentPadding = genreButtonContentPadding,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(
                    text = name,
                    color = textColor,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(ContentAlpha.medium)
                )
            }
        }

        @Composable
        fun GenreSection() {
            Column(modifier = Modifier.fillMaxWidth()) {
                FindSectionTitle("Genres")

                Spacer(modifier = Modifier.size(16.dp))

                GenreDivider()

                var hide by remember { mutableStateOf(true) }

                val items = if (hide) genres.subList(0, 4) else genres

                items.forEach {
                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        GenreItem(name = it) {}

                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowRight,
                            tint = Color.White,
                            contentDescription = "",
                            modifier = Modifier.alpha(ContentAlpha.medium)
                        )
                    }

                    GenreDivider()
                }

                if (hide) {
                    GenreItem(
                        name = "See more",
                        textColor = PrimeBlue,
                        onClick = { hide = false }
                    )
                }
            }
        }

        @Composable
        fun Content() {
            Box(modifier = Modifier.fillMaxSize()) {
                GradientsBackgroundCanvas(
                    drawBlue = true,
                    drawPurple = true
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.size(4.dp))

                    SearchBox()

                    BrowseBySection()

                    GenreSection()
                }
            }
        }
    }
}