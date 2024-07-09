package features.shop.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import core.presentation.utils.getKoinViewModel
import features.shop.presentation.screens.home_screen.HomeScreen
import features.shop.presentation.screens.home_screen.HomeScreenViewModel
import features.shop.presentation.screens.product_details_screen.ProductDetailsScreen
import features.shop.presentation.screens.product_details_screen.ProductDetailsViewModel
import features.shop.presentation.utils.HOME_SCREEN
import features.shop.presentation.utils.PRODUCT_DETAILS_SCREEN
import features.shop.presentation.utils.SHOP_GRAPH_ROUTE



fun NavGraphBuilder.shopNavGraph(
    navController: NavController,
    productDetailsViewModel: ProductDetailsViewModel
){

    navigation(startDestination = HOME_SCREEN, route = SHOP_GRAPH_ROUTE){
        composable(HOME_SCREEN){
            val homeScreenViewModel = getKoinViewModel<HomeScreenViewModel>()
            HomeScreen(
                viewModel = homeScreenViewModel,
                onProductClick = {
                    productDetailsViewModel.onProductSelected(it)
                    navController.navigate(PRODUCT_DETAILS_SCREEN){
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(PRODUCT_DETAILS_SCREEN){
            ProductDetailsScreen(
                viewModel = productDetailsViewModel,
            )
        }
    }
}