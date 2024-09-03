package features.shop.presentation.screens.orders_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import core.data.utils.DataResult
import features.shop.domain.models.Order
import features.shop.domain.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrdersViewModel(
    private val orderRepository: OrderRepository
): ViewModel() {

    private val _activeOrdersState = MutableStateFlow<ActiveOrdersState>(ActiveOrdersState.Empty)
    val activeOrdersState = _activeOrdersState.asStateFlow()

    private val _completedOrdersState = MutableStateFlow<CompletedOrdersState>(CompletedOrdersState.Empty)
    val completedOrdersState = _completedOrdersState.asStateFlow()


    fun fetchActiveOrders() = viewModelScope.launch {
        _activeOrdersState.value = ActiveOrdersState.Loading

        when(val activeOrders = orderRepository.getActiveOrders()){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _activeOrdersState.value = ActiveOrdersState.Error("Unable to fetch orders. Try again later")
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _activeOrdersState.value = ActiveOrdersState.Success(activeOrders.data)
            }
        }
    }

    fun fetchCompletedOrders() = viewModelScope.launch {
        _completedOrdersState.value = CompletedOrdersState.Loading

        when(val completedOrders = orderRepository.getCompletedOrders()){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _completedOrdersState.value = CompletedOrdersState.Error("Unable to fetch orders. Try again later")
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _completedOrdersState.value = CompletedOrdersState.Success(completedOrders.data)
            }
        }
    }
}