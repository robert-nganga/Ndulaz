package features.shop.presentation.screens.add_location_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.utils.DataResult
import core.domain.InputValidation
import features.shop.domain.models.PlaceDetail
import features.shop.domain.models.ShippingAddress
import features.shop.domain.repository.LocationRepository
import features.shop.domain.repository.ShippingAddressRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddLocationViewModel(
    private val locationRepository: LocationRepository,
    private val shippingAddressRepository: ShippingAddressRepository,
    private val inputValidation: InputValidation
): ViewModel() {

    private val _addLocationScreenState = MutableStateFlow(AddLocationScreenState())
    val addLocationScreenState = _addLocationScreenState.asStateFlow()

    private val _suggestionsState = MutableStateFlow<PlaceSuggestionsState>(PlaceSuggestionsState.Idle)
    val suggestionsState = _suggestionsState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private var searchJob: Job? = null



    fun onEvent(event: AddLocationScreenEvents){
        when(event){
            is AddLocationScreenEvents.OnBuildingNameChange -> {
                _addLocationScreenState.update {
                    it.copy(
                        buildingName = event.buildingName
                    )
                }
            }
            is AddLocationScreenEvents.OnDoorNumberChange -> {
                _addLocationScreenState.update {
                    it.copy(
                        doorNumber = event.doorNumber
                    )
                }
            }
            is AddLocationScreenEvents.OnFloorNumberChange -> {
                _addLocationScreenState.update {
                    it.copy(
                        floorNumber = event.floorNumber
                    )
                }
            }
            is AddLocationScreenEvents.OnPhoneNumberChange -> {
                _addLocationScreenState.update {
                    it.copy(
                        phoneNumber = event.phoneNumber
                    )
                }
            }

            is AddLocationScreenEvents.OnQueryChange -> {
                println("Search location inside the viemodel:: ${event.query}")
                onQueryChange(event.query)
            }

            is AddLocationScreenEvents.OnPlaceSelected -> {
                _addLocationScreenState.update {
                    it.copy(
                        selectedPlace = event.place
                    )
                }
            }

            is AddLocationScreenEvents.OnSaveAddress -> {
                if (_addLocationScreenState.value.selectedPlace == null){
                    _addLocationScreenState.update {
                        it.copy(
                            errorMessage = "Location not selected"
                        )
                    }
                    return
                }
                if (isLocationDetailsValid()){
                    saveAddress(createShippingAddress())
                }
            }

            is AddLocationScreenEvents.OnDeleteShippingAddress -> {
                deleteAddress(event.id)
            }

            is AddLocationScreenEvents.OnUpdateAddress -> {
                if (_addLocationScreenState.value.selectedPlace == null){
                    _addLocationScreenState.update {
                        it.copy(
                            errorMessage = "Location not selected"
                        )
                    }
                    return
                }
                if (isLocationDetailsValid()){
                    saveAddress(createShippingAddress(event.id))
                }
            }
        }
    }

    private fun deleteAddress(id: Int) = viewModelScope.launch {
        val result = shippingAddressRepository.deleteAddress(id)
        when(result){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _addLocationScreenState.update {
                    it.copy(
                        errorMessage = "Error deleting address"
                    )
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _addLocationScreenState.update {
                    it.copy (
                        isAddressDeleted = true
                    )
                }
            }
        }
    }

    fun updateShippingAddress(address: ShippingAddress){
        _addLocationScreenState.update {
            it.copy(
                buildingName = address.buildingName,
                doorNumber = address.doorNumber,
                floorNumber = address.floorNumber,
                phoneNumber = address.phoneNumber,
                selectedPlace = PlaceDetail(
                    name = address.name,
                    formattedAddress = address.formattedAddress,
                    placeId = address.placeId,
                    lat = address.lat,
                    lng = address.lng
                )
            )
        }
    }

    fun resetErrorMessage(){
        _addLocationScreenState.update {
            it.copy(
                errorMessage = null
            )
        }
    }

    fun resetAddLocationState(){
        _addLocationScreenState.update {
            AddLocationScreenState()
        }
    }

    fun resetSuggestionState(){
        _suggestionsState.update {
            PlaceSuggestionsState.Idle
        }
        _query.value = ""
    }

    private fun createShippingAddress(id: Int = 0): ShippingAddress {
        return ShippingAddress(
            id = id,
            placeId = _addLocationScreenState.value.selectedPlace!!.placeId,
            formattedAddress = _addLocationScreenState.value.selectedPlace!!.formattedAddress,
            name = _addLocationScreenState.value.selectedPlace!!.name,
            buildingName = _addLocationScreenState.value.buildingName,
            doorNumber = _addLocationScreenState.value.doorNumber,
            floorNumber = _addLocationScreenState.value.floorNumber,
            phoneNumber = _addLocationScreenState.value.phoneNumber,
            lat = _addLocationScreenState.value.selectedPlace!!.lat,
            lng = _addLocationScreenState.value.selectedPlace!!.lng
        )
    }
    private fun isLocationDetailsValid(): Boolean {
        val phoneErrorMsg = inputValidation.validateField(_addLocationScreenState.value.phoneNumber, title = "Phone number")
        val doorNumberErrorMsg = inputValidation.validateField(_addLocationScreenState.value.doorNumber, title = "Door number")
        val floorNumberErrorMsg = inputValidation.validateField(_addLocationScreenState.value.floorNumber, title = "Floor number")

        _addLocationScreenState.update {
            it.copy(
                phoneNumberError = phoneErrorMsg,
                doorNumberError = doorNumberErrorMsg,
                floorNumberError = floorNumberErrorMsg
            )
        }

        return phoneErrorMsg == null && doorNumberErrorMsg == null && floorNumberErrorMsg == null
    }

    private fun saveAddress(address: ShippingAddress) = viewModelScope.launch {
        _addLocationScreenState.update {
            it.copy(
                isLoading = true
            )
        }
        val response = shippingAddressRepository.upsertAddress(address)
        when(response){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _addLocationScreenState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = response.message
                    )
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _addLocationScreenState.update {
                    it.copy(
                        isLoading = false,
                        isAddressSaved = true
                    )
                }
            }
        }
    }


    private fun onQueryChange(query: String) {
        _query.update {
            query
        }
        // Creates a delay to avoid making too many network calls
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(700L)
            if (query.length >= 2){
                println("Searching query:: $query")
                getSuggestions(query)
            } else {
                _suggestionsState.update {
                    PlaceSuggestionsState.Idle
                }
            }
        }
    }

    private fun getSuggestions(query: String) = viewModelScope.launch {
        _suggestionsState.update {
            PlaceSuggestionsState.Loading
        }
        when(val result = locationRepository.getPlaceSuggestions(query)){
            is DataResult.Empty -> {}
            is DataResult.Error -> {
                _suggestionsState.update {
                    PlaceSuggestionsState.Error(result.message)
                }
            }
            is DataResult.Loading -> {}
            is DataResult.Success -> {
                _suggestionsState.update {
                    PlaceSuggestionsState.Success(result.data)
                }
            }
        }
    }


}