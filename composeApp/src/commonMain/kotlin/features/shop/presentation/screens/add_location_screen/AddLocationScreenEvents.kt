package features.shop.presentation.screens.add_location_screen

sealed interface AddLocationScreenEvents {
    data class OnPhoneNumberChange(val phoneNumber: String): AddLocationScreenEvents
    data class OnFloorNumberChange(val floorNumber: String): AddLocationScreenEvents
    data class OnDoorNumberChange(val doorNumber: String): AddLocationScreenEvents
    data class OnBuildingNameChange(val buildingName: String): AddLocationScreenEvents
}