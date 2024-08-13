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
import features.shop.presentation.components.SwipeToDeleteContainer

@Composable
fun CartScreen(
    viewModel: CartViewModel,
    onNavigateToCheckout: (List<CartItem>) -> Unit
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
    ){ paddingValues ->
        CartScreenContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            cartItems = cartItems,
            totalPrice = totalPrice,
            onItemQuantityChange = { item, quantity ->
                viewModel.updateItemQuantity(item, quantity)
            },
            onDeleteItem = {
                viewModel.deleteItem(it.id)
            },
            onCheckout = {
                onNavigateToCheckout(cartItems)
            }
        )
    }
}

@Composable
fun CartScreenContent(
    modifier: Modifier = Modifier,
    cartItems: List<CartItem>,
    onItemQuantityChange: (CartItem, Int) -> Unit,
    onDeleteItem: (CartItem) -> Unit,
    totalPrice: Double,
    onCheckout: () -> Unit
){
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 125.dp
            )
        ){
            items(
                items = cartItems,
                key = {it.id}
            ){ item ->
                SwipeToDeleteContainer(
                    item = item,
                    onDelete = onDeleteItem
                ){
                    CartItemInfo(
                        modifier = Modifier,
                        cartItem = item,
                        onItemQuantityChanged = onItemQuantityChange
                    )
                }
            }
        }
        CheckOutSection(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            onCheckOutClick = onCheckout,
            totalPrice = "Ksh $totalPrice",
            isCheckoutEnabled = cartItems.isNotEmpty()
        )
    }

}

@Composable
fun CheckOutSection(
    modifier: Modifier = Modifier,
    onCheckOutClick: ()-> Unit,
    isCheckoutEnabled: Boolean,
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
                shape = RoundedCornerShape(16.dp),
                enabled = isCheckoutEnabled
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