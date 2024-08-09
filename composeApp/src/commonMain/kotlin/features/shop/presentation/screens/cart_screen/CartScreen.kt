package features.shop.presentation.screens.cart_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ShoppingCartCheckout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import features.shop.domain.models.CartItem
import features.shop.presentation.components.CartItemInfo
import org.koin.compose.koinInject

@Composable
fun CartScreen(
    viewModel: CartViewModel
){

    val cartItems by viewModel.cartItems.collectAsState(emptyList())
    val totalPrice by viewModel.totalPrice.collectAsState(0.0)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Cart",
                        style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.clearCart()
                        }
                    ){
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = "Delete icon",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
            )
        },
        backgroundColor = MaterialTheme.colors.onBackground.copy(
            alpha = 0.035f
        )
    ){
        CartScreenContent(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            cartItems = cartItems,
            totalPrice = totalPrice,
            onItemQuantityChange = {item, quantity ->
                viewModel.updateItemQuantity(item, quantity)
            }
        )
    }
}

@Composable
fun CartScreenContent(
    modifier: Modifier = Modifier,
    cartItems: List<CartItem>,
    onItemQuantityChange: (CartItem, Int) -> Unit,
    totalPrice: Double,
){
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(
                bottom = 110.dp
            )
        ){
            items(cartItems){ cartItem ->
                CartItemInfo(
                    modifier = Modifier,
                    cartItem = cartItem,
                    onItemQuantityChanged = onItemQuantityChange
                )
            }
        }
        CheckOutSection(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            onCheckOutClick = {},
            totalPrice = "Ksh $totalPrice"
        )
    }

}

@Composable
fun CheckOutSection(
    modifier: Modifier = Modifier,
    onCheckOutClick: ()-> Unit,
    totalPrice: String
){
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colors.background)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ){
            Column {
                Text(
                    "Total price",
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.onSurface.copy(
                            alpha = 0.5f
                        )
                    )
                )
                Text(
                    totalPrice,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1.0f))
            Button(
                onClick = onCheckOutClick,
                shape = RoundedCornerShape(16.dp)
            ){
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Checkout",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        Icons.Outlined.ShoppingCartCheckout,
                        contentDescription = ""
                    )
                }
            }

        }
    }
}