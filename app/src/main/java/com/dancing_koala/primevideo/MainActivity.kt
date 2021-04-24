package com.dancing_koala.primevideo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import com.dancing_koala.primevideo.ui.AppScreen
import com.dancing_koala.primevideo.ui.theme.PrimeVideoTheme
import com.github.zsoltk.compose.backpress.BackPressHandler
import com.github.zsoltk.compose.backpress.LocalBackPressHandler

class MainActivity : ComponentActivity() {

    private val backPressHandler = BackPressHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalBackPressHandler provides backPressHandler) {
                PrimeVideoTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        AppScreen.Content()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (!backPressHandler.handle()) {
            super.onBackPressed()
        }
    }
}

