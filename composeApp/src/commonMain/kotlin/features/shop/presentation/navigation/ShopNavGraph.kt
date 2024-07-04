package features.shop.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import features.shop.presentation.screens.home_screen.HomeScreen
import features.shop.presentation.screens.home_screen.HomeScreenViewModel
import features.shop.presentation.utils.HOME_SCREEN
import features.shop.presentation.utils.SHOP_GRAPH_ROUTE


fun NavGraphBuilder.shopNavGraph(
    navController: NavController,
    homeScreenViewModel: HomeScreenViewModel
){
    navigation(startDestination = HOME_SCREEN, route = SHOP_GRAPH_ROUTE){
        composable(HOME_SCREEN){
            HomeScreen(
                viewModel = homeScreenViewModel,
            )
        }
    }
}