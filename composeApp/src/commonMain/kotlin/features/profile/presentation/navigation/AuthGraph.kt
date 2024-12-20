package features.profile.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import features.profile.presentation.screens.AuthViewModel
import features.profile.presentation.screens.login_screen.LoginScreen
import features.profile.presentation.screens.onboarding_screen.OnboardingScreen
import features.profile.presentation.screens.signup_screen.SignUpScreen
import features.profile.presentation.utils.AUTH_GRAPH_ROUTE
import features.profile.presentation.utils.LOGIN_SCREEN
import features.profile.presentation.utils.SIGNUP_SCREEN
import features.shop.presentation.utils.ONBOARDING_SCREEN
import features.shop.presentation.utils.SHOP_GRAPH_ROUTE


fun NavGraphBuilder.authNavGraph(navController: NavController, authViewModel: AuthViewModel){
    navigation(
        route = AUTH_GRAPH_ROUTE,
        startDestination = ONBOARDING_SCREEN
    ){
        composable(route = ONBOARDING_SCREEN) {
            OnboardingScreen(
                onNavigateToLogin = {
                    navController.navigate(LOGIN_SCREEN){
                        launchSingleTop = true
                        popUpTo(ONBOARDING_SCREEN){
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = LOGIN_SCREEN){
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToSignUp = {
                    navController.navigate(SIGNUP_SCREEN)
                },
                onNavigateToHome = {
                    navController.navigate(SHOP_GRAPH_ROUTE){
                        launchSingleTop = true
                        popUpTo(AUTH_GRAPH_ROUTE){
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = SIGNUP_SCREEN){
            SignUpScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = {
                    navController.navigate(LOGIN_SCREEN)
                },
                onNavigateToHome = {
                    navController.navigate(SHOP_GRAPH_ROUTE){
                        launchSingleTop = true
                        popUpTo(AUTH_GRAPH_ROUTE){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}