package com.muss_coding.core.presentation.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Our custom brand palette
val MidnightBlue = Color(0xFF0D1B2A)
val SunriseCoral = Color(0xFFFF6B6B)
val StoneGrayLight = Color(0xFFF8F9FA) // Light mode background
val StoneGrayDark = Color(0xFF495057)   // Light mode text
val NearBlack = Color(0xFF1B263B)      // Dark mode background
val OffWhite = Color(0xFFE0E1DD)       // Dark mode text

// Material 3 Light Color Scheme
val LightColorScheme = lightColorScheme(
    primary = MidnightBlue,
    onPrimary = Color.White,
    secondary = SunriseCoral,
    onSecondary = Color.White,
    background = StoneGrayLight,
    onBackground = StoneGrayDark,
    surface = StoneGrayLight,
    onSurface = StoneGrayDark,
    error = Color(0xFFB00020),
    onError = Color.White
)

// Material 3 Dark Color Scheme
val DarkColorScheme = darkColorScheme(
    primary = SunriseCoral, // A vibrant primary for dark mode stands out well
    onPrimary = NearBlack,
    secondary = MidnightBlue,
    onSecondary = OffWhite,
    background = NearBlack,
    onBackground = OffWhite,
    surface = NearBlack,
    onSurface = OffWhite,
    error = Color(0xFFCF6679),
    onError = NearBlack
)