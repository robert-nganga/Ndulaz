package features.shop.presentation.screens.check_out_screen

import com.robert.request.OrderRequest
import features.shop.domain.models.OutOfStockError
import features.shop.domain.models.PaymentMethod
import features.shop.domain.models.ShippingAddress
import features.shop.domain.request.OrderItemRequest
import features.shop.domain.request.OrderStatus
import ndula.composeapp.generated.resources.Res
import ndula.composeapp.generated.resources.card
import ndula.composeapp.generated.resources.cash
import ndula.composeapp.generated.resources.mpesa
import ndula.composeapp.generated.resources.paypal

data class CheckOutScreenState(
    val totalPrice: Double = 0.0,
    val tax: Double = 0.0,
    val shipping: Double = 250.0,
    val selectedPaymentMethod: PaymentMethod = PaymentMethod(
        name = "Paypal",
        image = Res.drawable.paypal
    ),
    val selectedAddress: ShippingAddress? = null,
    val paymentMethods: List<PaymentMethod> = methods,
    val outOfStockItems: List<OutOfStockError> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
){
    fun createOrderRequest(items: List<OrderItemRequest>): OrderRequest {
        return OrderRequest(
            items = items,
            totalAmount = totalPrice + tax + shipping,
            status = OrderStatus.PROCESSING,
            shippingAddress = selectedAddress!!
        )
    }
}

val methods = listOf(
    PaymentMethod(
        name = "Paypal",
        image = Res.drawable.paypal
    ),
    PaymentMethod(
        name = "Mpesa",
        image = Res.drawable.mpesa
    ),
    PaymentMethod(
        name = "Card",
        image = Res.drawable.card
    ),
    PaymentMethod(
        name = "Cash",
        image = Res.drawable.cash
    )
)
