package features.shop.presentation.screens.order_details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import features.shop.domain.models.Order
import features.shop.domain.models.OrderItem
import features.shop.domain.models.OrderStatus
import features.shop.domain.models.ShippingAddress
import features.shop.domain.models.Step
import features.shop.presentation.components.OrderItemDetailsCard
import features.shop.presentation.components.OrderTracking
import features.shop.presentation.components.VerticalStepper
import features.shop.presentation.utils.NavigationUtils
import features.shop.presentation.utils.parseColorFromString
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import ndula.composeapp.generated.resources.Res
import ndula.composeapp.generated.resources.box
import ndula.composeapp.generated.resources.box_open
import ndula.composeapp.generated.resources.delivery
import ndula.composeapp.generated.resources.dollar_sign


@Composable
fun OrderDetailsScreen(
    order: Order = NavigationUtils.order,
    orderItemId: Int = NavigationUtils.orderItemId,
    onNavigateBack: () -> Unit
){
    val orderItem by remember {
        mutableStateOf(
            order.items.find { it.id == orderItemId }!!
        )
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Orders details")
                },
                elevation = 0.dp,
                backgroundColor = MaterialTheme.colors.background,
                navigationIcon = {
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(
                        onClick = {
                            onNavigateBack()
                        }
                    ){
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBackIos,
                            contentDescription = "Back icon"
                        )
                    }
                }
            )
        },
        backgroundColor = MaterialTheme.colors.onBackground.copy(
            alpha = 0.04f
        )
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    horizontal = 16.dp
                )
        ){
            Spacer(modifier = Modifier.height(16.dp))
            OrderItemDetailsCard(
                orderItem = orderItem
            )
            Spacer(modifier = Modifier.height(16.dp))
            OrderTracking()
//            OrderStatusSection(
//                order = order
//            )
        }
    }
}

@Composable
fun ShippingAddressDetails(
    modifier: Modifier = Modifier,
    shippingAddress: ShippingAddress
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            )
    ){
        Text(
            "Shipping Address",
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(start = 10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.onSurface.copy(
                            alpha = 0.1f
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ){
                Icon(
                    Icons.Outlined.Apartment,
                    contentDescription = "",
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ){
                Text(
                    shippingAddress.name,
                    style = MaterialTheme.typography.body1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    shippingAddress.formattedAddress,
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.onSurface.copy(
                            alpha = 0.5f
                        )
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Outlined.Phone,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                shippingAddress.phoneNumber,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun OrderStatusSection(
    modifier: Modifier = Modifier,
    order: Order
){
    val steps = listOf(
        Step(
            title = "Payment Confirmed",
            description = "Your payment has been confirmed",
            image = Res.drawable.dollar_sign,
        ),
        Step(
            title = "Processing",
            description = "Your order is being processed and will be delivered in a few",
            image = Res.drawable.box
        ),
        Step(
            title = "In Delivery",
            description = "Your order is on the way",
            image = Res.drawable.delivery
        ),
        Step(
            title = "Delivered",
            description = "Thank you for ordering with us",
            image = Res.drawable.box_open
        )
    )
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(16.dp)
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
        ){
            Text(
                "Order Tracking",
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            VerticalStepper(
                modifier = modifier,
                steps = steps,
                currentStep = order.status.getCurrentStep(),
            )
        }
    }
}

fun OrderStatus.getCurrentStep(): Int {
    return when(this){
        OrderStatus.PAYMENTCONFIRMED -> 0
        OrderStatus.PROCESSING -> 1
        OrderStatus.INDELIVERY -> 2
        OrderStatus.COMPLETED -> 3
    }
}