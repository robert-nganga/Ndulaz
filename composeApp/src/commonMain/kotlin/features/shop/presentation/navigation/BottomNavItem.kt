package features.shop.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val unFocusedIcon: ImageVector,
    val focusedIcon: ImageVector,
    val label: String
) {
    data object Home : BottomNavItem("home", Icons.Outlined.Home, Icons.Filled.Home,  "Home")
    data object Cart : BottomNavItem("cart", Icons.Outlined.ShoppingCart, Icons.Filled.ShoppingCart,  "Cart")
    data object WishList : BottomNavItem("wishList", Icons.Rounded.FavoriteBorder, Icons.Rounded.Favorite,  "WishList")
    data object Profile : BottomNavItem("profile", Icons.Outlined.AccountCircle, Icons.Filled.AccountCircle,  "Profile")

}