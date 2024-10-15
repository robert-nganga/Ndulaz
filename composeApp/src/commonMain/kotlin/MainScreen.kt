import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import core.presentation.utils.getKoinViewModel
import features.profile.presentation.navigation.authNavGraph
import features.profile.presentation.screens.AuthViewModel
import features.profile.presentation.utils.AUTH_GRAPH_ROUTE
import features.shop.presentation.navigation.BottomNavItem
import features.shop.presentation.navigation.shopNavGraph
import features.shop.presentation.screens.add_location_screen.AddLocationViewModel
import features.shop.presentation.screens.brand_screen.BrandScreenViewModel
import features.shop.presentation.screens.cart_screen.CartViewModel
import features.shop.presentation.screens.category_screen.CategoryViewModel
import features.shop.presentation.screens.check_out_screen.CheckOutViewModel
import features.shop.presentation.screens.home_screen.HomeScreenViewModel
import features.shop.presentation.screens.most_popular_screen.MostPopularScreenViewModel
import features.shop.presentation.screens.orders_screen.OrdersViewModel
import features.shop.presentation.screens.product_details_screen.ProductDetailsViewModel
import features.shop.presentation.screens.profile_screen.ProfileViewModel
import features.shop.presentation.screens.review_screen.ReviewViewModel
import features.shop.presentation.screens.search_screen.SearchViewModel
import features.shop.presentation.screens.settings_screen.SettingsViewModel
import features.shop.presentation.screens.wish_list_screen.WishListViewModel
import features.shop.presentation.utils.PRODUCT_DETAILS_SCREEN
import features.shop.presentation.utils.SHOP_GRAPH_ROUTE


@Composable
fun MainScreen(
    navController: NavHostController,
    isLoggedIn: Boolean,
    authViewModel: AuthViewModel
){
    val productDetailsViewModel = getKoinViewModel<ProductDetailsViewModel>()
    val brandViewModel = getKoinViewModel<BrandScreenViewModel>()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val cartViewModel = getKoinViewModel<CartViewModel>()
    val searchViewModel = getKoinViewModel<SearchViewModel>()
    val checkOutViewModel = getKoinViewModel<CheckOutViewModel>()
    val addLocationViewModel = getKoinViewModel<AddLocationViewModel>()
    val profileViewModel = getKoinViewModel<ProfileViewModel>()
    val homeScreenViewModel = getKoinViewModel<HomeScreenViewModel>()
    val wishListViewModel = getKoinViewModel<WishListViewModel>()
    val ordersViewModel = getKoinViewModel<OrdersViewModel>()
    val reviewViewModel = getKoinViewModel<ReviewViewModel>()
    val mostPopularScreenViewModel = getKoinViewModel<MostPopularScreenViewModel>()
    val categoryScreenViewModel = getKoinViewModel<CategoryViewModel>()
    val settingsScreenViewModel = getKoinViewModel<SettingsViewModel>()

    val cartItems by cartViewModel.cartItems.collectAsState()

    val showBottomBar = when(navBackStackEntry?.destination?.route){
        BottomNavItem.Home.route -> true
        BottomNavItem.Cart.route -> true
        BottomNavItem.WishList.route -> true
        BottomNavItem.Profile.route -> true
        else -> false
    }

    val enableStatusPadding = when(navBackStackEntry?.destination?.route){
        PRODUCT_DETAILS_SCREEN -> false
        else -> true
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar){
                BottomNavigationBar(
                    navController = navController,
                    navBackStackEntry = navController.currentBackStackEntryAsState().value,
                    cartItemsCount = cartItems.size
                )
            }
        }
    ){
        NavHost(
            modifier = if (enableStatusPadding) Modifier.navigationBarsPadding().systemBarsPadding() else Modifier.navigationBarsPadding(),
            navController = navController,
            startDestination = if (isLoggedIn) SHOP_GRAPH_ROUTE else AUTH_GRAPH_ROUTE
        ){
            authNavGraph(navController, authViewModel)
            shopNavGraph(
                navController,
                productDetailsViewModel,
                brandViewModel,
                cartViewModel,
                searchViewModel,
                checkOutViewModel,
                addLocationViewModel,
                profileViewModel,
                homeScreenViewModel,
                wishListViewModel,
                ordersViewModel,
                reviewViewModel,
                mostPopularScreenViewModel,
                categoryScreenViewModel,
                settingsScreenViewModel
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
    cartItemsCount: Int,
    modifier: Modifier = Modifier
){

    val currentRoute = navBackStackEntry?.destination

    val screens = listOf(
        BottomNavItem.Home,
        BottomNavItem.Cart,
        BottomNavItem.WishList,
        BottomNavItem.Profile
    )

    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 5.dp
    ){
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                navController = navController,
                currentDestination = currentRoute,
                badgeCount = if (screen.route == BottomNavItem.Cart.route) cartItemsCount else null
            )
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: BottomNavItem,
    navController: NavHostController,
    currentDestination: NavDestination?,
    badgeCount: Int? = null
){
    val isSelected = currentDestination?.route == screen.route

    BottomNavigationItem(
        icon = {
            BadgedBox(
                badge = {
                    if(badgeCount != null && badgeCount > 0){
                        Badge(
                            modifier = Modifier.align(Alignment.Center),
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = MaterialTheme.colors.onPrimary
                        ){
                            Text(
                                text = "$badgeCount",
                                style = TextStyle(
                                    color = MaterialTheme.colors.onPrimary
                                )
                            )
                        }
                    }
                }
            ){
                Icon(
                    imageVector = if (isSelected) screen.focusedIcon else screen.unFocusedIcon,
                    contentDescription = "Navigation Icon",
                    modifier = Modifier.size(26.dp),
                )
            }
            Icon(
                imageVector = if (isSelected) screen.focusedIcon else screen.unFocusedIcon,
                contentDescription = "Navigation Icon",
                modifier = Modifier.size(26.dp),
            )
        },
        selected = isSelected,
        label = {
                Text(
                    screen.label
                )
        },
        onClick = {
            navController.navigate(screen.route){
                popUpTo(BottomNavItem.Home.route)
                launchSingleTop = true
            }
        },
        alwaysShowLabel = true,
        selectedContentColor = MaterialTheme.colors.primary,
        unselectedContentColor = MaterialTheme.colors.primary.copy(alpha = 0.5f)
    )

}