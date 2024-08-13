package features.shop.presentation.screens.check_out_screen

import features.shop.domain.models.PaymentMethod
import features.shop.domain.models.ShippingAddress
import ndula.composeapp.generated.resources.Res
import ndula.composeapp.generated.resources.paypal

data class CheckOutScreenState(
    val totalPrice: Double = 0.0,
    val tax: Double = 0.0,
    val shipping: Double = 250.0,
    val selectedPaymentMethod: PaymentMethod = PaymentMethod(
        name = "Paypal",
        image = Res.drawable.paypal
    ),
    val selectedAddress: ShippingAddress = ShippingAddress(
        name = "Kahawa Wendani",
        lat = 0.0,
        lng = 0.0,
        phone = "0712345678"
    )
)
