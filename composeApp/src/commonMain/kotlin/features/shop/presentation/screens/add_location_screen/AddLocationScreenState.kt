package features.shop.presentation.screens.add_location_screen

import features.shop.domain.models.PlaceDetail

data class AddLocationScreenState(
    val selectedPlace: PlaceDetail? = null,
    val phoneNumber: String = "",
    val buildingName: String = "",
    val floorNumber: String = "",
    val doorNumber: String = ""
)
