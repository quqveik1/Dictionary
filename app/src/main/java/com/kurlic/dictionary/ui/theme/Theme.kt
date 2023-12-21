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
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = DarkGray,
    secondary = MediumGray,
    tertiary = LightGray,
    background = DarkGray,
    surface = MediumGray,
    onPrimary = PureWhite,
    onSecondary = PureWhite,
    onTertiary = PureWhite,
    onBackground = DarkGray,
    onSurface = PureWhite,
)

private val LightColorScheme = lightColorScheme(
    primary = OffWhite,
    secondary = LightGray,
    tertiary = MediumGray,
    background = OffWhite,
    surface = PureWhite,
    onPrimary = DarkGray,
    onSecondary = DarkGray,
    onTertiary = DarkGray,
    onBackground = DarkGray,
    onSurface = DarkGray
)

@Composable
fun DictionaryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(
                window,
                view
            ).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}