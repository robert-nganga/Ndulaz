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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import features.shop.domain.models.Order
import features.shop.domain.models.OrderStatus
import features.shop.domain.models.ShippingAddress
import features.shop.presentation.components.OrderItemDetailsCard
import features.shop.presentation.components.OrderStage
import features.shop.presentation.components.OrderTracking
import features.shop.presentation.utils.NavigationUtils


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
                    Text(
                        "Orders details",
                        style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                elevation = 0.dp,
                backgroundColor = MaterialTheme.colors.background,
                navigationIcon = {
                    Spacer(modifier = Modifier.width(10.dp))
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
                .verticalScroll(rememberScrollState())
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
            OrderStatusSection(
                modifier = Modifier,
                order = order
            )
            Spacer(modifier = Modifier.height(16.dp))
            ShippingAddressDetails(
                modifier = Modifier,
                shippingAddress = order.shippingAddress
            )
        }
    }
}

@Composable
fun ShippingAddressDetails(
    modifier: Modifier = Modifier,
    shippingAddress: ShippingAddress
){
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(16.dp)
    ){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 10.dp
                )
        ){
            Text(
                "Shipping Address",
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
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
        }
    }
}

@Composable
fun OrderStatusSection(
    modifier: Modifier = Modifier,
    order: Order
){
    val stages = OrderStatus.entries.map {
        OrderStage(
            status = it.getStepName(),
            message = it.getStepDescription(
                location = order.shippingAddress.formattedAddress
            ),
            completed = it.ordinal <= order.status.ordinal
        )
    }
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
                "Order Status",
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            OrderTracking(
                stages = stages
            )
        }
    }
}

fun OrderStatus.getStepDescription(location: String): String {
    return when(this){
        OrderStatus.PAYMENTCONFIRMED -> "Payment received successfully."
        OrderStatus.PROCESSING -> "Order is being processed."
        OrderStatus.INDELIVERY -> location
        OrderStatus.COMPLETED -> "Order delivered successfully."
    }
}
fun OrderStatus.getStepName(): String {
    return when(this){
        OrderStatus.PAYMENTCONFIRMED -> "Payment Confirmed"
        OrderStatus.PROCESSING -> "Processing"
        OrderStatus.INDELIVERY -> "In Delivery"
        OrderStatus.COMPLETED -> "Completed"
    }
}