package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = GmBlueDarkPrimary,
    onPrimary = Color(0xFF00325B),
    primaryContainer = GmBlueDarkContainer,
    onPrimaryContainer = Color(0xFFD1E4FF),
    secondary = GmOrangeDarkPrimary,
    onSecondary = Color(0xFF4E2600),
    secondaryContainer = GmOrangeDarkContainer,
    onSecondaryContainer = Color(0xFFFFDCC2),
    tertiary = Color(0xFF80DEEA),
    background = GmBackgroundDark,
    surface = GmSurfaceDark,
    surfaceVariant = GmCardDark,
    onBackground = Color(0xFFE2E8F0),
    onSurface = Color(0xFFE2E8F0)
)

private val LightColorScheme = lightColorScheme(
    primary = GmBluePrimary,
    onPrimary = Color.White,
    primaryContainer = GmBlueContainer,
    onPrimaryContainer = GmOnBlueContainer,
    secondary = GmOrangeSecondary,
    onSecondary = Color.White,
    secondaryContainer = GmOrangeContainer,
    onSecondaryContainer = GmOnOrangeContainer,
    tertiary = GmCyanTertiary,
    onTertiary = Color.White,
    tertiaryContainer = GmCyanContainer,
    onTertiaryContainer = GmOnCyanContainer,
    background = GmBackgroundLight,
    surface = GmCardSurface,
    surfaceVariant = GmBlueLight,
    outline = GmOutlineLight,
    onBackground = Color(0xFF0F172A),
    onSurface = Color(0xFF0F172A)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set false to maintain distinctive GM TECH branding
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
