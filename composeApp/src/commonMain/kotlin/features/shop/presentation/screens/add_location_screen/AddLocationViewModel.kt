package features.shop.presentation.screens.add_location_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import features.shop.domain.models.PlaceDetail
import features.shop.domain.repository.LocationRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddLocationViewModel(
    private val locationRepository: LocationRepository
): ViewModel() {

    private val _addLocationScreenState = MutableStateFlow(AddLocationScreenState())
    val addLocationScreenState = _addLocationScreenState.asStateFlow()

    private val _suggestions = MutableStateFlow<List<PlaceDetail>>(emptyList())
    val suggestions = _suggestions.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private var searchJob: Job? = null



    fun onEvent(events: AddLocationScreenEvents){
        when(events){
            is AddLocationScreenEvents.OnBuildingNameChange -> {
                _addLocationScreenState.update {
                    it.copy(
                        buildingName = events.buildingName
                    )
                }
            }
            is AddLocationScreenEvents.OnDoorNumberChange -> {
                _addLocationScreenState.update {
                    it.copy(
                        doorNumber = events.doorNumber
                    )
                }
            }
            is AddLocationScreenEvents.OnFloorNumberChange -> {
                _addLocationScreenState.update {
                    it.copy(
                        floorNumber = events.floorNumber
                    )
                }
            }
            is AddLocationScreenEvents.OnPhoneNumberChange -> {
                _addLocationScreenState.update {
                    it.copy(
                        phoneNumber = events.phoneNumber
                    )
                }
            }
        }
    }


    fun onQueryChange(query: String) {
        _query.update {
            query
        }
        // Creates a delay to avoid making too many network calls
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(700L)
            if (query.length >= 2){
                //searchShoes(query)
            }else{
                //_searchState.update { SearchScreenState.Idle(_suggestions.value) }
            }
        }
    }

    private fun getSuggestions(query: String) = viewModelScope.launch{

    }


}