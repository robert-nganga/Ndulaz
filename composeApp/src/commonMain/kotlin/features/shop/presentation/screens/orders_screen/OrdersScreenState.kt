package features.shop.presentation.screens.orders_screen

import features.shop.domain.models.Order

sealed interface ActiveOrdersState{
    data class Success(val orders: List<Order>): ActiveOrdersState
    data object Loading: ActiveOrdersState
    data class Error(val message: String): ActiveOrdersState
    data object Empty: ActiveOrdersState
}

sealed interface CompletedOrdersState{
    data class Success(val orders: List<Order>): CompletedOrdersState
    data object Loading: CompletedOrdersState
    data class Error(val message: String): CompletedOrdersState
    data object Empty: CompletedOrdersState
}