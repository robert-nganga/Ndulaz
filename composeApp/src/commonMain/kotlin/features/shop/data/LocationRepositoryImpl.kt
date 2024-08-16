package features.shop.data

import core.data.utils.BASE_URL
import core.data.utils.DataResult
import core.data.utils.dataResultSafeApiCall
import features.shop.domain.models.LatLng
import features.shop.domain.models.PlaceDetail
import features.shop.domain.repository.LocationRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class LocationRepositoryImpl(
    private val httpClient: HttpClient
): LocationRepository {
    override suspend fun getPlaceSuggestions(query: String): DataResult<List<PlaceDetail>> = dataResultSafeApiCall{
        val response = httpClient.get("$BASE_URL/location/autocomplete"){
            parameter("query", query)
        }
        response.body<List<PlaceDetail>>()
    }

    override suspend fun getPlaceDetailsFromCoordinates(coordinates: LatLng): DataResult<PlaceDetail> = dataResultSafeApiCall{
        val response = httpClient.post("$BASE_URL/location"){
            setBody(coordinates)
        }
        response.body<PlaceDetail>()
    }
}