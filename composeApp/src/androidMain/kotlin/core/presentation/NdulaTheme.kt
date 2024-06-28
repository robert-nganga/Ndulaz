package core.presentation

import android.app.Activity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import core.presentation.ui.buildMaterialTheme
import core.presentation.ui.darkColorScheme
import core.presentation.ui.lightColorScheme

@Composable
actual fun NdulaTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    // TODO: Dynamic color
//    val context = LocalContext.current
//    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !dynamicColor){
//        val dynamicColor = dynamicDarkColorScheme(context)
//        dynamicColor.copy(
//            secondary = dynamicColor.primary
//        )
//    }
    val colorScheme = when{
        darkTheme -> buildMaterialTheme(darkColorScheme, false)
        else -> buildMaterialTheme(lightColorScheme, true)
    }

    val view = LocalView.current
    if (!view.isInEditMode){
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(
                window,
                view
            ).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colors = colorScheme,
        content = content,
    )
}