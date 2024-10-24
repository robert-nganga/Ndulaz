package core.presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import core.presentation.ui.blueColorScheme
import core.presentation.ui.buildMaterialTheme
import core.presentation.ui.darkColorScheme
import core.presentation.ui.lightColorScheme
import core.presentation.ui.typography
import features.shop.presentation.screens.settings_screen.ThemeSelection

@Composable
actual fun NdulaTheme(
    themeSelection: ThemeSelection,
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
){
    val colorScheme = when(themeSelection){
        ThemeSelection.Light -> buildMaterialTheme(lightColorScheme, true)
        ThemeSelection.Dark -> buildMaterialTheme(darkColorScheme, false)
        ThemeSelection.LightBlue -> buildMaterialTheme(blueColorScheme, true)
        ThemeSelection.System -> if(darkTheme) buildMaterialTheme(darkColorScheme, false) else buildMaterialTheme(
            lightColorScheme, true)
    }
    MaterialTheme(
        colors = colorScheme,
        content = content,
        typography = typography
    )

}