package features.shop.presentation.screens.add_location_screen

import features.shop.domain.models.PlaceDetail

data class AddLocationScreenState(
    val selectedPlace: PlaceDetail? = null,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val buildingName: String = "",
    val buildingNameError: String? = null,
    val floorNumber: String = "",
    val floorNumberError: String? = null,
    val doorNumber: String = "",
    val doorNumberError: String? = null,
    val isLoading: Boolean = false,
    val isAddressSaved: Boolean = false,
    val errorMessage: String? = null
)

sealed interface PlaceSuggestionsState {
    data class Success(val suggestions: List<PlaceDetail>): PlaceSuggestionsState
    data object Loading: PlaceSuggestionsState
    data class Error(val message: String): PlaceSuggestionsState
    data object Idle: PlaceSuggestionsState
}
