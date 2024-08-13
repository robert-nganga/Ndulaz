package features.shop.presentation.screens.check_out_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import features.shop.domain.models.CartItem
import features.shop.domain.models.PaymentMethod
import features.shop.domain.models.ShippingAddress
import features.shop.presentation.components.CheckOutItem
import ndula.composeapp.generated.resources.Res
import ndula.composeapp.generated.resources.banner1
import ndula.composeapp.generated.resources.paypal
import org.jetbrains.compose.resources.painterResource

@Composable
fun CheckOutScreen(
    viewModel: CheckOutViewModel,
    onNavigateBack: () -> Unit,
){
    val state by viewModel.checkOutScreenState.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Order review")
                },
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack
                    ){
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBackIos,
                            contentDescription = "Back icon"
                        )
                    }
                }
            )
        },
        bottomBar = {
            CheckOutScreenBottomBar(
                onCheckout = {}
            )
        }
    ){ paddingValues ->
        CheckOutScreenContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            cartItems = cartItems,
            state = state,
            selectedPaymentMethod = state.selectedPaymentMethod,
            selectedAddress = state.selectedAddress,
            onAddressChange = {},
            onPaymentMethodChange = {},
            onPromoEntered = {}
        )
    }
}

@Composable
fun CheckOutScreenBottomBar(
    modifier: Modifier = Modifier,
    onCheckout: () -> Unit
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            )
    ){
        Button(
            onClick = onCheckout,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ){
            Text(
                "Checkout",
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}

@Composable
fun CheckOutScreenContent(
    modifier: Modifier = Modifier,
    cartItems: List<CartItem>,
    state: CheckOutScreenState,
    selectedPaymentMethod: PaymentMethod,
    selectedAddress: ShippingAddress,
    onAddressChange: () -> Unit,
    onPaymentMethodChange: () -> Unit,
    onPromoEntered: (String) -> Unit,
){
    LazyColumn(
        modifier = modifier
    ){
        items(
            cartItems,
            key = {
                it.id
            }
        ){ item ->
            CheckOutItem(
                cartItem = item
            )
        }
        item("promo_section") {
            PromoSection(
                onPromoEntered = onPromoEntered
            )
        }
        item("price_details_section") {
            PriceDetailsSection(
                totalCartPrice = state.totalPrice,
                tax = state.tax,
                shippingCost = state.shipping,
                modifier = Modifier,
                selectedPaymentMethod = selectedPaymentMethod,
                selectedAddress = selectedAddress,
                onAddressChange = onAddressChange,
                onPaymentMethodChange = onPaymentMethodChange
            )
        }
    }
}

@Composable
fun PriceDetailsSection(
    modifier: Modifier = Modifier,
    totalCartPrice: Double,
    tax: Double,
    shippingCost: Double,
    selectedPaymentMethod: PaymentMethod,
    selectedAddress: ShippingAddress,
    onAddressChange: () -> Unit,
    onPaymentMethodChange: () -> Unit,
){
    Box(
        modifier = modifier
            .padding(16.dp)
            .border(
                width = 1.5.dp,
                color = MaterialTheme.colors.primary.copy(
                    alpha = 0.2f
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
    ){
        Column(
            modifier = modifier
                .padding(16.dp)
        ){
            PriceItem(
                title = "Subtotal",
                price = totalCartPrice
            )
            Spacer(modifier = Modifier.height(12.dp))
            PriceItem(
                title = "Tax",
                price = tax
            )
            Spacer(modifier = Modifier.height(12.dp))
            PriceItem(
                title = "Shipping Cost",
                price = shippingCost
            )
            Spacer(modifier = Modifier.height(16.dp))
            PriceItem(
                title = "Total",
                price = totalCartPrice + tax + shippingCost,
                isTotal = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            Divider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            PaymentMethodSection(
                paymentMethod = selectedPaymentMethod,
                onChangePaymentMethod = onPaymentMethodChange
            )
            Spacer(modifier = Modifier.height(28.dp))
            Divider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(10.dp))
            ShippingAddressSection(
                address = selectedAddress,
                onAddressChange = onAddressChange
            )
        }
    }
}

@Composable
fun ShippingAddressSection(
    modifier: Modifier = Modifier,
    address: ShippingAddress,
    onAddressChange: () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxWidth()
    ){
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                "Shipping address",
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = onAddressChange
            ){
                Text(
                    "Change",
                    style = MaterialTheme.typography.body1
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            address.name,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Icon(
                Icons.Outlined.Phone,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(address.phone)
        }
    }
}

@Composable
fun PaymentMethodSection(
    modifier: Modifier = Modifier,
    paymentMethod: PaymentMethod,
    onChangePaymentMethod: () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxWidth()
    ){
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                "Payment method",
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = onChangePaymentMethod
            ){
                Text(
                    "Change",
                    style = MaterialTheme.typography.body1
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(resource = paymentMethod.image),
                contentDescription = "Payment method icon",
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                paymentMethod.name,
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun PriceItem(
    modifier: Modifier = Modifier,
    title: String,
    price: Double,
    isTotal: Boolean = false
){
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            title,
            style = MaterialTheme.typography.h6.copy(
                color = MaterialTheme.colors.onSurface.copy(
                    alpha = if (isTotal) 1f else 0.5f
                ),
                fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Medium,
                fontSize = if (isTotal) 20.sp else 16.sp
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Ksh $price",
            style = MaterialTheme.typography.h6.copy(
                color = MaterialTheme.colors.onSurface.copy(
                    alpha = if (isTotal) 1f else 0.5f
                ),
                fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Medium,
                fontSize = if (isTotal) 20.sp else 16.sp
            )
        )
    }
}

@Composable
fun PromoSection(
    modifier: Modifier = Modifier,
    onPromoEntered: (String) -> Unit
){
    var promo by remember{
        mutableStateOf("")
    }

    Box(
        modifier = modifier
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary.copy(
                    alpha = 0.2f
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .padding(12.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            TextField(
                value = promo,
                onValueChange = { promo = it },
                placeholder = {
                    Text(
                        "Have a promo code? Enter here",
                        style = MaterialTheme.typography.body2.copy(
                            letterSpacing = 0.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                  onPromoEntered(promo)
                },
                shape = RoundedCornerShape(16.dp),
                enabled = promo.length >= 5
            ){
                Text(
                    "Apply",
                    style = MaterialTheme.typography.body2.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                )
            }

        }
    }
}