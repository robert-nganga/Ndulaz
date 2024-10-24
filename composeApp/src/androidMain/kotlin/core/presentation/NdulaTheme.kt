package core.presentation

import android.app.Activity
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
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
) {
    // TODO: Dynamic color
//    val context = LocalContext.current
//    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !dynamicColor){
//        val dynamicColors = dynamicDarkColorScheme(context)
//        dynamicColors.copy(
//            secondary = dynamicColors.primary,
//        )
//    }

//    val colorScheme = when {
//        darkTheme -> buildMaterialTheme(darkColorScheme, false)
//        else -> buildMaterialTheme(lightColorScheme, true)
//    }

    val themeColors = when(themeSelection){
        ThemeSelection.Light -> buildMaterialTheme(lightColorScheme, true)
        ThemeSelection.Dark -> buildMaterialTheme(darkColorScheme, false)
        ThemeSelection.LightBlue -> buildMaterialTheme(blueColorScheme, true)
        ThemeSelection.System -> if(darkTheme) buildMaterialTheme(darkColorScheme, false) else buildMaterialTheme(
            lightColorScheme, true)
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = themeColors.background.toArgb()
            window.navigationBarColor = themeColors.background.toArgb()
            WindowCompat.getInsetsController(
                window,
                view
            ).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(
                window,
                view
            ).isAppearanceLightNavigationBars = !darkTheme
        }
    }


    MaterialTheme(
        colors = themeColors,
        content = content,
        typography = typography
    )
}