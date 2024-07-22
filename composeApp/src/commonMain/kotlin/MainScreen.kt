import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import features.shop.presentation.screens.product_details_screen.ProductDetailsViewModel
import features.shop.presentation.utils.PRODUCT_DETAILS_SCREEN
import features.shop.presentation.utils.SHOP_GRAPH_ROUTE


@Composable
fun MainScreen(
    navController: NavHostController,
    isLoggedIn: Boolean,
    authViewModel: AuthViewModel
){
    val productDetailsViewModel = getKoinViewModel<ProductDetailsViewModel>()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

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
                    navBackStackEntry = navController.currentBackStackEntryAsState().value
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
            shopNavGraph(navController, productDetailsViewModel)
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
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
                currentDestination = currentRoute
            )
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: BottomNavItem,
    navController: NavHostController,
    currentDestination: NavDestination?
){
    val isSelected = currentDestination?.route == screen.route

    BottomNavigationItem(
        icon = {
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