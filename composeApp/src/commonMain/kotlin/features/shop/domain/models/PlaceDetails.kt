package features.shop.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class PlaceDetail(
    val name: String,
    val formattedAddress: String,
    val lat: Double,
    val lng: Double,
    val placeId: String
)

