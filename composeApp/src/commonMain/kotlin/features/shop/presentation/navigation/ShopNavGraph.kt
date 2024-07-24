package features.shop.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import core.presentation.utils.getKoinViewModel
import features.shop.presentation.screens.home_screen.HomeScreen
import features.shop.presentation.screens.home_screen.HomeScreenViewModel
import features.shop.presentation.screens.most_popular_screen.MostPopularScreen
import features.shop.presentation.screens.most_popular_screen.MostPopularScreenViewModel
import features.shop.presentation.screens.product_details_screen.ProductDetailsScreen
import features.shop.presentation.screens.product_details_screen.ProductDetailsViewModel
import features.shop.presentation.utils.MOST_POPULAR_SCREEN
import features.shop.presentation.utils.PRODUCT_DETAILS_SCREEN
import features.shop.presentation.utils.SHOP_GRAPH_ROUTE


fun NavGraphBuilder.shopNavGraph(
    navController: NavController,
    productDetailsViewModel: ProductDetailsViewModel
){

    navigation(startDestination = BottomNavItem.Home.route, route = SHOP_GRAPH_ROUTE){
        composable(BottomNavItem.Home.route){
            val homeScreenViewModel = getKoinViewModel<HomeScreenViewModel>()
            HomeScreen(
                viewModel = homeScreenViewModel,
                onProductClick = {
                    productDetailsViewModel.onProductSelected(it)
                    navController.navigate(PRODUCT_DETAILS_SCREEN){
                        launchSingleTop = true
                    }
                },
                onNavigateToMostPopular = {
                    navController.navigate(MOST_POPULAR_SCREEN){
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(BottomNavItem.Cart.route){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    "Cart route"
                )
            }
        }

        composable(BottomNavItem.WishList.route){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    "Wish list route"
                )
            }
        }

        composable(BottomNavItem.Profile.route){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    "Profile route"
                )
            }
        }

        composable(PRODUCT_DETAILS_SCREEN){
            ProductDetailsScreen(
                viewModel = productDetailsViewModel,
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable(MOST_POPULAR_SCREEN){
            val mostPopularScreenViewModel = getKoinViewModel<MostPopularScreenViewModel>()
            MostPopularScreen(
                viewModel = mostPopularScreenViewModel,
                onNavigateBack = {
                     navController.navigateUp()
                },
                onShoeClick = {
                    productDetailsViewModel.onProductSelected(it)
                    navController.navigate(PRODUCT_DETAILS_SCREEN){
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}