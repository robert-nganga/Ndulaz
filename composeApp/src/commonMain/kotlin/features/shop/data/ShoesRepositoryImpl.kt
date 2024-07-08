package features.shop.data

import core.data.utils.BASE_URL
import core.data.utils.DataResult
import core.data.utils.dataResultSafeApiCall
import features.shop.domain.models.Category
import features.shop.domain.models.Shoe
import features.shop.domain.models.ShoeResponse
import features.shop.domain.repository.ShoesRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ShoesRepositoryImpl(
    private val httpClient: HttpClient
): ShoesRepository {
    override suspend fun getShoes(page: Int, limit: Int): DataResult<List<Shoe>> = dataResultSafeApiCall {
        val response = httpClient.get("$BASE_URL/shoes/all") {
            parameter("page", page)
            parameter("pageSize", limit)
        }
        val shoeResponse = response.body<ShoeResponse>()
        shoeResponse.shoes
    }
    override suspend fun getCategories(): DataResult<List<Category>> = dataResultSafeApiCall {
        val response = httpClient.get("$BASE_URL/categories/all"){
        }
        val categoryResponse = response.body<List<Category>>()
        categoryResponse
    }

    override suspend fun getShoeById(id: Int): DataResult<Shoe> {
        TODO("Not yet implemented")
    }

    override suspend fun filterShoesByCategory(category: String): DataResult<List<Shoe>> {
        TODO("Not yet implemented")
    }

    override suspend fun filterShoesByBrand(brand: String): DataResult<List<Shoe>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchShoes(query: String): DataResult<List<Shoe>> {
        TODO("Not yet implemented")
    }
}