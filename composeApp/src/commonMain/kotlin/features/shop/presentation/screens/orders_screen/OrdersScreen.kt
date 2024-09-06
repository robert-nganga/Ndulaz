package features.shop.presentation.screens.orders_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import features.shop.domain.models.Order
import features.shop.domain.models.toLocalizedString
import features.shop.presentation.components.CompletedOrderItemDetails
import features.shop.presentation.components.OrderItemDetails
import kotlin.random.Random


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrdersScreen(
    onNavigateBack: () -> Unit,
    onNavigateToOrderDetails: (Order, Int) -> Unit,
    viewModel: OrdersViewModel
){
    val tabs = listOf(
        "Active",
        "Completed"
    )

    var selectedTabIndex by remember {
        mutableStateOf(0)
    }

    val pagerState = rememberPagerState{
        tabs.size
    }

    val activeOrdersState by viewModel.activeOrdersState.collectAsState()
    val completedOrdersState by viewModel.completedOrdersState.collectAsState()

    LaunchedEffect(selectedTabIndex){
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress){
        if (!pagerState.isScrollInProgress){
            selectedTabIndex = pagerState.currentPage
        }
    }

    LaunchedEffect(Unit){
        viewModel.fetchCompletedOrders()
        viewModel.fetchActiveOrders()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("My Orders")
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
            alpha = 0.08f
        )
    ){ paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            TabRow(
                selectedTabIndex = selectedTabIndex,
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground,
            ){
                tabs.forEachIndexed { index, item ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                        },
                        text = {
                            Text(item)
                        }
                    )
                }
            }

            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = pagerState
            ){ index ->
                when (index) {
                    0 -> {
                        ActiveOrdersSection(
                            modifier = Modifier
                                .fillMaxSize(),
                            activeOrdersState = activeOrdersState,
                            onTrackOrderClick = { order, itemId ->
                                onNavigateToOrderDetails(order, itemId)
                            }
                        )
                    }
                    1 -> {
                        CompletedOrdersSection(
                            modifier = Modifier
                                .fillMaxSize(),
                            completedOrdersState = completedOrdersState,
                            onLeaveReviewClicked = {}
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CompletedOrdersSection(
    modifier: Modifier = Modifier,
    completedOrdersState: CompletedOrdersState,
    onLeaveReviewClicked: ()-> Unit
){
    when(completedOrdersState){
        is CompletedOrdersState.Empty -> {}
        is CompletedOrdersState.Error -> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ){
                Text(completedOrdersState.message)
            }
        }
        is CompletedOrdersState.Loading -> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        is CompletedOrdersState.Success -> {
            val orders = completedOrdersState.orders
            val orderItems = orders.map {it.items}.flatten()
            LazyColumn(
                modifier = modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 11.dp
                    )
            ){
                items(orderItems){ item ->
                    val order = orders.find { it.id == item.orderId }!!
                    CompletedOrderItemDetails(
                        orderItem = item,
                        buttonText = "Leave Review",
                        onButtonClick = onLeaveReviewClicked,
                        hasReview = Random.nextBoolean(),
                        status = order.status.toLocalizedString()
                    )
                }
            }
        }
    }
}

@Composable
fun ActiveOrdersSection(
    modifier: Modifier = Modifier,
    activeOrdersState: ActiveOrdersState,
    onTrackOrderClick: (Order, Int) -> Unit
){
    when(activeOrdersState){
        is ActiveOrdersState.Empty -> {}
        is ActiveOrdersState.Error -> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ){
                Text(activeOrdersState.message)
            }
        }
        is ActiveOrdersState.Loading -> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        is ActiveOrdersState.Success -> {
            val orders = activeOrdersState.orders
            val orderItems = orders.map {it.items}.flatten()
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 11.dp
                )
            ){
                items(orderItems){ item ->
                    val order = orders.find { it.id == item.orderId }!!
                    OrderItemDetails(
                        orderItem = item,
                        buttonText = "Track Order",
                        onButtonClick = {
                            onTrackOrderClick(order, item.id)
                        },
                        status = order.status.toLocalizedString()
                    )
                }
            }
        }
    }
}