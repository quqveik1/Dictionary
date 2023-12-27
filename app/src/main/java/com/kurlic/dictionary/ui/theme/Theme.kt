package com.kurlic.dictionary.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import com.kurlic.dictionary.R


@Composable
fun DictionaryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colorScheme = darkColorScheme(
        primary = colorResource(id = R.color.dark_gray),
        secondary = colorResource(id = R.color.medium_gray),
        tertiary = colorResource(id = R.color.light_gray),
        background = colorResource(id = R.color.dark_gray),
        surface = colorResource(id = R.color.medium_gray),
        onPrimary = colorResource(id = R.color.pure_white),
        onSecondary = colorResource(id = R.color.pure_white),
        onTertiary = colorResource(id = R.color.pure_white),
        onBackground = colorResource(id = R.color.dark_gray),
        onSurface = colorResource(id = R.color.pure_white),
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(
                window,
                view
            ).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
