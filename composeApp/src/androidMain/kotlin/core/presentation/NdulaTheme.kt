package core.presentation

import android.app.Activity
import android.os.Build
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import core.presentation.ui.buildMaterialTheme
import core.presentation.ui.darkColorScheme
import core.presentation.ui.lightColorScheme
import core.presentation.ui.typography

@Composable
actual fun NdulaTheme(
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

    val colorScheme = when {
        darkTheme -> buildMaterialTheme(darkColorScheme, false)
        else -> buildMaterialTheme(lightColorScheme, true)
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
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
        colors = colorScheme,
        content = content,
        typography = typography
    )
}