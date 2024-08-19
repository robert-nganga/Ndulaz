package features.shop.presentation.screens.add_location_screen

import features.shop.domain.models.PlaceDetail
import features.shop.domain.models.ShippingAddress

sealed interface AddLocationScreenEvents {
    data class OnPhoneNumberChange(val phoneNumber: String): AddLocationScreenEvents
    data class OnFloorNumberChange(val floorNumber: String): AddLocationScreenEvents
    data class OnDoorNumberChange(val doorNumber: String): AddLocationScreenEvents
    data class OnBuildingNameChange(val buildingName: String): AddLocationScreenEvents
    data class OnPlaceSelected(val place: PlaceDetail): AddLocationScreenEvents
    data class OnQueryChange(val query: String): AddLocationScreenEvents
    data object OnSaveAddress : AddLocationScreenEvents
}