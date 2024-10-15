package features.shop.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import core.presentation.utils.getKoinViewModel
import features.shop.presentation.screens.AddressScreen
import features.shop.presentation.screens.settings_screen.SettingsScreen
import features.shop.presentation.screens.add_location_screen.AddLocationScreen
import features.shop.presentation.screens.add_location_screen.AddLocationViewModel
import features.shop.presentation.screens.all_brands_screen.AllBrandsScreen
import features.shop.presentation.screens.all_brands_screen.AllBrandsViewModel
import features.shop.presentation.screens.brand_screen.BrandScreen
import features.shop.presentation.screens.brand_screen.BrandScreenViewModel
import features.shop.presentation.screens.cart_screen.CartScreen
import features.shop.presentation.screens.cart_screen.CartViewModel
import features.shop.presentation.screens.category_screen.CategoryScreen
import features.shop.presentation.screens.category_screen.CategoryViewModel
import features.shop.presentation.screens.check_out_screen.CheckOutScreen
import features.shop.presentation.screens.check_out_screen.CheckOutViewModel
import features.shop.presentation.screens.edit_profile_screen.EditProfileScreen
import features.shop.presentation.screens.edit_profile_screen.EditProfileViewModel
import features.shop.presentation.screens.home_screen.HomeScreen
import features.shop.presentation.screens.home_screen.HomeScreenViewModel
import features.shop.presentation.screens.most_popular_screen.MostPopularScreen
import features.shop.presentation.screens.most_popular_screen.MostPopularScreenViewModel
import features.shop.presentation.screens.order_details_screen.OrderDetailsScreen
import features.shop.presentation.screens.orders_screen.OrdersScreen
import features.shop.presentation.screens.orders_screen.OrdersViewModel
import features.shop.presentation.screens.payment_success_screen.PaymentSuccessScreen
import features.shop.presentation.screens.product_details_screen.ProductDetailsScreen
import features.shop.presentation.screens.product_details_screen.ProductDetailsViewModel
import features.shop.presentation.screens.profile_screen.ProfileScreen
import features.shop.presentation.screens.profile_screen.ProfileViewModel
import features.shop.presentation.screens.review_screen.ReviewScreen
import features.shop.presentation.screens.review_screen.ReviewViewModel
import features.shop.presentation.screens.search_screen.SearchScreen
import features.shop.presentation.screens.search_screen.SearchViewModel
import features.shop.presentation.screens.settings_screen.SettingsViewModel
import features.shop.presentation.screens.wish_list_screen.WishListScreen
import features.shop.presentation.screens.wish_list_screen.WishListViewModel
import features.shop.presentation.utils.ADDRESS_SCREEN
import features.shop.presentation.utils.ADD_LOCATION_SCREEN
import features.shop.presentation.utils.ALL_BRANDS_SCREEN
import features.shop.presentation.utils.BRAND_SCREEN
import features.shop.presentation.utils.CATEGORY_SCREEN
import features.shop.presentation.utils.CHECK_OUT_SCREEN
import features.shop.presentation.utils.EDIT_PROFILE_SCREEN
import features.shop.presentation.utils.MOST_POPULAR_SCREEN
import features.shop.presentation.utils.NavigationUtils
import features.shop.presentation.utils.ORDERS_SCREEN
import features.shop.presentation.utils.ORDER_DETAILS_SCREEN
import features.shop.presentation.utils.PAYMENT_SUCCESS_SCREEN
import features.shop.presentation.utils.PRODUCT_DETAILS_SCREEN
import features.shop.presentation.utils.REVIEW_SCREEN
import features.shop.presentation.utils.SEARCH_SCREEN
import features.shop.presentation.utils.SETTINGS_SCREEN
import features.shop.presentation.utils.SHOP_GRAPH_ROUTE
import org.koin.compose.viewmodel.koinViewModel


fun NavGraphBuilder.shopNavGraph(
    navController: NavController,
    productDetailsViewModel: ProductDetailsViewModel,
    brandViewModel: BrandScreenViewModel,
    cartViewModel: CartViewModel,
    searchViewModel: SearchViewModel,
    checkOutViewModel: CheckOutViewModel,
    addLocationViewModel: AddLocationViewModel,
    profileViewModel: ProfileViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    wishListViewModel: WishListViewModel,
    ordersViewModel: OrdersViewModel,
    reviewViewModel: ReviewViewModel,
    mostPopularScreenViewModel: MostPopularScreenViewModel
){

    navigation(startDestination = BottomNavItem.Home.route, route = SHOP_GRAPH_ROUTE){
        composable(BottomNavItem.Home.route){
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
                },
                onNavigateToBrand = {
                    NavigationUtils.brand = it
                    navController.navigate(BRAND_SCREEN){
                        launchSingleTop = true
                    }
                },
                onNavigateToAllBrands = {
                    navController.navigate(ALL_BRANDS_SCREEN){
                        launchSingleTop = true
                    }
                },
                onNavigateToSearch = {
                    navController.navigate(SEARCH_SCREEN){
                        launchSingleTop = true
                    }
                },
                onNavigateToNotifications = {
                    navController.navigate(ADDRESS_SCREEN) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(BottomNavItem.Cart.route){
            CartScreen(
                viewModel = cartViewModel,
                onNavigateToCheckout = { items ->
                    NavigationUtils.cartItems = items
                    navController.navigate(CHECK_OUT_SCREEN){
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(BottomNavItem.WishList.route){
            WishListScreen(
                viewModel = wishListViewModel,
               onShoeClick = {}
            )
        }

        composable(SEARCH_SCREEN){
            SearchScreen(
                viewModel = searchViewModel,
                onNavigateBack = {
                    navController.navigateUp()
                },
                onShoeClick = {
                    productDetailsViewModel.onProductSelected(it)
                    navController.navigate(PRODUCT_DETAILS_SCREEN) {
                        launchSingleTop = true
                    }
                },
                onCategoryClicked = {
                    NavigationUtils.category = it
                    navController.navigate(CATEGORY_SCREEN){
                        launchSingleTop = true
                    }
                },
                onBrandClicked = {
                    NavigationUtils.brand = it
                    navController.navigate(BRAND_SCREEN){
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(BottomNavItem.Profile.route){
            ProfileScreen(
                viewModel = profileViewModel,
                onNavigateToEditProfile = {
                    NavigationUtils.currentUser = it
                    navController.navigate(EDIT_PROFILE_SCREEN){
                        launchSingleTop = true
                    }
                },
                onNavigateToOrdersScreen = {
                    navController.navigate(ORDERS_SCREEN){
                        launchSingleTop = true
                    }
                },
                onNavigateToSettingsScreen = {
                    navController.navigate(SETTINGS_SCREEN){
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(PRODUCT_DETAILS_SCREEN){
            ProductDetailsScreen(
                viewModel = productDetailsViewModel,
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToCart = {
                    navController.navigate(BottomNavItem.Cart.route){
                        popUpTo(PRODUCT_DETAILS_SCREEN){ inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(CHECK_OUT_SCREEN){
            CheckOutScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                viewModel = checkOutViewModel,
                onNavigateToAddLocation = { shippingAddress->
                    NavigationUtils.shippingAddress = shippingAddress
                    navController.navigate(ADD_LOCATION_SCREEN){
                        launchSingleTop = true
                    }
                },
                onNavigateToPaymentSuccess = {
                    navController.navigate(PAYMENT_SUCCESS_SCREEN){
                        popUpTo(CHECK_OUT_SCREEN){ inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(MOST_POPULAR_SCREEN){
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
        composable(BRAND_SCREEN){
            BrandScreen(
                viewModel = brandViewModel,
                onShoeClick = {
                    productDetailsViewModel.onProductSelected(it)
                    navController.navigate(PRODUCT_DETAILS_SCREEN){
                        launchSingleTop = true
                    }
                },
                onNavigateBack = {
                     navController.navigateUp()
                }
            )
        }
        composable(ALL_BRANDS_SCREEN){
            val allBrandsViewModel = getKoinViewModel<AllBrandsViewModel>()
            AllBrandsScreen(
                viewModel = allBrandsViewModel,
                onNavigateBack = {
                  navController.navigateUp()
                },
                onBrandClick = {
                    NavigationUtils.brand = it
                    navController.navigate(BRAND_SCREEN){
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(ADD_LOCATION_SCREEN){
            AddLocationScreen(
                viewModel = addLocationViewModel,
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(PAYMENT_SUCCESS_SCREEN){
            PaymentSuccessScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToOrderScreen = {
                    navController.navigate(ORDERS_SCREEN){
                        popUpTo(PAYMENT_SUCCESS_SCREEN){ inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(EDIT_PROFILE_SCREEN){
            val editProfileViewModel = getKoinViewModel<EditProfileViewModel>()
            EditProfileScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                viewModel = editProfileViewModel,
                currentUser = NavigationUtils.currentUser
            )
        }
        composable(ORDERS_SCREEN){
            OrdersScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                viewModel = ordersViewModel,
                onNavigateToOrderDetails = {order, orderItemId->
                    NavigationUtils.order = order
                    NavigationUtils.orderItemId = orderItemId
                    navController.navigate(ORDER_DETAILS_SCREEN){
                        launchSingleTop = true
                    }
                },
                onNavigateToReview = {
                    NavigationUtils.orderItem = it
                    navController.navigate(REVIEW_SCREEN){
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(ORDER_DETAILS_SCREEN){
            OrderDetailsScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(REVIEW_SCREEN){
            ReviewScreen(
                viewModel = reviewViewModel,
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(CATEGORY_SCREEN) {
            val categoryScreenViewModel = getKoinViewModel<CategoryViewModel>()
            CategoryScreen(
                viewModel = categoryScreenViewModel,
                onNavigateBack = {
                    navController.navigateUp()
                },
                category = NavigationUtils.category
            )
        }
        composable(SETTINGS_SCREEN) {
            val settingsScreenViewModel = koinViewModel<SettingsViewModel>()
            SettingsScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                viewModel = settingsScreenViewModel
            )
        }
        composable(ADDRESS_SCREEN) {
            AddressScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}