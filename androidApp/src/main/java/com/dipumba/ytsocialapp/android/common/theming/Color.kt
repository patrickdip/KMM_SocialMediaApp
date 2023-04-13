package com.dipumba.ytsocialapp.android.common.theming

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Blue = Color(0xFF1E80F8)
val Gray = Color(0xFFF3F3F4)


val Black = Color(0xFF000000)

val Black87 = Color(0xFF18191A)
val DarkGray = Color(0xFF999A9A)

val Black54 = Color(0xFF373B3F)
val Black24 = Color(0xFF242526)


val White = Color(0xFFFFFFFF)

val White87 = Color(0xFFE2E2E2)
val LightGray = Color(0xFF8A8A8D)

val White36 = Color(0xFFE5E5E5)
val White76 = Color(0xFFF5F5F5)

internal val LightColors = lightColors(
    primary = Blue,
    primaryVariant = Blue,
    background = White76,
    onBackground = Black87,
    surface = White,
    onSurface = Black87
)

internal val DarkColors = darkColors(
    primary = Blue,
    primaryVariant = Blue,
    background = Black87,
    onBackground = White87,
    surface = Black24,
    onSurface = White87
)