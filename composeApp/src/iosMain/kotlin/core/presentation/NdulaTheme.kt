package core.presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import core.presentation.ui.buildMaterialTheme
import core.presentation.ui.darkColorScheme
import core.presentation.ui.lightColorScheme
import core.presentation.ui.typography

@Composable
actual fun NdulaTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
){
    MaterialTheme(
        colors = if(darkTheme) buildMaterialTheme(darkColorScheme, false) else buildMaterialTheme(
            lightColorScheme, true),
        content = content,
        typography = typography
    )

}