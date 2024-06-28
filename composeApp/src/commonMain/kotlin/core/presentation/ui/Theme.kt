package core.presentation.ui

import androidx.compose.material.Colors

fun buildMaterialTheme(colors: ThemeColors, isLight: Boolean) : Colors {
    return Colors(
        primary = colors.primary,
        secondary = colors.secondary,
        primaryVariant = colors.primary,
        secondaryVariant = colors.secondary,
        background = colors.background,
        surface = colors.background,
        onBackground = colors.onBackGround,
        onSurface = colors.onSurface,
        error = colors.error,
        onError = colors.onError,
        onPrimary = colors.onPrimary,
        onSecondary = colors.onSecondary,
        isLight = isLight
    )
}