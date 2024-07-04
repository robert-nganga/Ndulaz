import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import core.data.preferences.SessionHandler
import core.presentation.NdulaTheme
import core.presentation.utils.getKoinViewModel
import features.profile.presentation.navigation.authNavGraph
import features.profile.presentation.screens.AuthViewModel
import features.profile.presentation.utils.AUTH_GRAPH_ROUTE
import features.shop.presentation.navigation.shopNavGraph
import features.shop.presentation.screens.home_screen.HomeScreenViewModel
import features.shop.presentation.utils.SHOP_GRAPH_ROUTE
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.getKoin

@Composable
@Preview
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean
) {
    KoinContext {
        NdulaTheme(
            darkTheme = darkTheme,
            dynamicColor = dynamicColor
        ) {
            Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxSize()
            ){
                val authViewModel = getKoinViewModel<AuthViewModel>()
                val homeScreenViewModel = getKoinViewModel<HomeScreenViewModel>()
                val navController = rememberNavController()
                val sessionHandler = getKoin().get<SessionHandler>()
                var isUserLoggedIn by remember {
                    mutableStateOf(false)
                }
                LaunchedEffect(Unit){
                    sessionHandler.getUser().collect{ user->
                        if (user == null) {
                            navController.navigate(AUTH_GRAPH_ROUTE) {
                                launchSingleTop = true
                                popUpTo(SHOP_GRAPH_ROUTE) {
                                    inclusive = true
                                }
                            }
                        } else{
                            isUserLoggedIn = true
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = if (isUserLoggedIn) SHOP_GRAPH_ROUTE else AUTH_GRAPH_ROUTE
                ){
                    authNavGraph(navController, authViewModel)
                    shopNavGraph(navController, homeScreenViewModel)
                }

            }
        }
    }
}