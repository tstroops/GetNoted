package com.example.getnoted.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color


// 1. Light Color Scheme (Default)
private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = White,
    secondary = Gray700,
    onSecondary = White,
    surface = BluePrimary,      // Your Top Bar will be Blue
    onSurface = White,          // Text on Top Bar will be White
    background = OffWhite,      // App background
    onBackground = Gray700      // Default text color
)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */


@Composable
fun GetNotedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    // We set this to false so your BLUE theme is always used instead of the phone's wallpaper colors
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
