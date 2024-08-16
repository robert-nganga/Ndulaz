package features.shop.domain.repository

import androidx.compose.ui.layout.LayoutCoordinates
import core.data.utils.DataResult
import features.shop.domain.models.LatLng
import features.shop.domain.models.PlaceDetail

interface LocationRepository {

    suspend fun getPlaceSuggestions(query: String): DataResult<List<PlaceDetail>>

    suspend fun getPlaceDetailsFromCoordinates(coordinates: LatLng): DataResult<PlaceDetail>
}