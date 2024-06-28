import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import core.presentation.NdulaTheme
import features.profile.presentation.navigation.authNavGraph
import features.profile.presentation.utils.AUTH_GRAPH_ROUTE
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean
) {
    NdulaTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ){

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = AUTH_GRAPH_ROUTE
            ){
                authNavGraph(navController)
            }

        }
    }
}