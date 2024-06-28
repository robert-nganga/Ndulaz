package features.profile.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import features.profile.presentation.login_screen.LoginScreen
import features.profile.presentation.signup_screen.SignUpScreen
import features.profile.presentation.utils.AUTH_GRAPH_ROUTE
import features.profile.presentation.utils.LOGIN_SCREEN
import features.profile.presentation.utils.SIGNUP_SCREEN


fun NavGraphBuilder.authNavGraph(navController: NavController){
    navigation(
        route = AUTH_GRAPH_ROUTE,
        startDestination = LOGIN_SCREEN
    ){
        composable(route = LOGIN_SCREEN){
            LoginScreen()
        }

        composable(route = SIGNUP_SCREEN){
            SignUpScreen()
        }
    }
}