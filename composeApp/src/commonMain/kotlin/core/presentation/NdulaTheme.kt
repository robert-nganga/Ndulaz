package core.presentation

import androidx.compose.runtime.Composable
import features.shop.presentation.screens.settings_screen.ThemeSelection

@Composable
expect fun NdulaTheme(
    themeSelection: ThemeSelection,
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
)