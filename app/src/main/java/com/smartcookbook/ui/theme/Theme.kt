package com.smartcookbook.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary        = Orange500,
    onPrimary      = White,
    primaryContainer = Orange100,
    onPrimaryContainer = Gray900,
    secondary      = Gray900,
    onSecondary    = White,
    background     = White,
    onBackground   = Gray900,
    surface        = White,
    onSurface      = Gray900,
    surfaceVariant = Gray50,
    onSurfaceVariant = Gray500,
    outline        = Gray200,
)

@Composable
fun SmartCookBookTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography  = Typography,
        content     = content
    )
}
