package com.robert.request

import features.shop.domain.models.ShippingAddress
import features.shop.domain.request.OrderItemRequest
import features.shop.domain.request.OrderStatus
import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    val items: List<OrderItemRequest>,
    val totalAmount: Double,
    val status: OrderStatus,
    val shippingAddress: ShippingAddress,
)
