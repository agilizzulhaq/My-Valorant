package com.agilizzulhaq.submissionakhiraplikasiandroid.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColors(
    primary = Red700,
    primaryVariant = Red200,
    secondary = Teal200
)

private val LightColorScheme = lightColors(
    primary = Red700,
    primaryVariant = Red500,
    secondary = Teal200
)

@Composable
fun SubmissionAkhirAplikasiAndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit)
{
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}