import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import core.presentation.NdulaTheme
import core.presentation.utils.getKoinViewModel
import features.profile.presentation.screens.AuthStatus
import features.profile.presentation.screens.AuthViewModel
import features.profile.presentation.utils.AUTH_GRAPH_ROUTE
import features.shop.presentation.utils.SHOP_GRAPH_ROUTE
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

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
                val navController = rememberNavController()

                val authStatus by authViewModel.isLoggedIn.collectAsState()

                var loggedIn by rememberSaveable {
                    mutableStateOf(0)
                }

                LaunchedEffect(authStatus, loggedIn){
                    println("isUserLoggedIn: $authStatus")
                    if (loggedIn > 0 && authStatus == AuthStatus.LoggedOut){
                        println("Performing logout")
                        if (authStatus == AuthStatus.LoggedOut){
                            navController.navigate(AUTH_GRAPH_ROUTE) {
                                launchSingleTop = true
                                popUpTo(SHOP_GRAPH_ROUTE) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }

                when(authStatus){
                    AuthStatus.Loading -> {
                        println("Loading")
                        // Display splash screen
                    }
                    else -> {
                        println("Start destination is $authStatus")
                        if(authStatus == AuthStatus.LoggedIn){
                            LaunchedEffect(Unit){
                                loggedIn++
                            }
                        }
                        MainScreen(
                            authViewModel =authViewModel,
                            navController = navController,
                            isLoggedIn = authStatus == AuthStatus.LoggedIn
                        )
//                        NavHost(
//                            navController = navController,
//                            startDestination = if (authStatus == AuthStatus.LoggedIn) SHOP_GRAPH_ROUTE else AUTH_GRAPH_ROUTE
//                        ){
//                            authNavGraph(navController, authViewModel)
//                            shopNavGraph(navController, productDetailsViewModel)
//                        }

                    }
                }



            }
        }
    }
}