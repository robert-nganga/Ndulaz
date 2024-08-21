package features.shop.presentation.screens.check_out_screen

import features.shop.domain.models.PaymentMethod
import features.shop.domain.models.ShippingAddress
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
)

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
