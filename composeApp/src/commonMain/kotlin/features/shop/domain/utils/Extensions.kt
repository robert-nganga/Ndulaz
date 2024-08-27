package features.shop.domain.utils

import features.shop.domain.models.OutOfStockError
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json


fun String.parseOutOfStockError(): List<OutOfStockError> {
    return try {
        val json = Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
        json.decodeFromString<List<OutOfStockError>>(this)
    } catch (e: SerializationException){
        e.printStackTrace()
        emptyList()
    }
}