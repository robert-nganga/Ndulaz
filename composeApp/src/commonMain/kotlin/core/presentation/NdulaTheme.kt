package core.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun NdulaTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
)