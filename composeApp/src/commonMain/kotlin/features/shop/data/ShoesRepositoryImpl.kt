package features.shop.data

import core.data.utils.BASE_URL
import core.data.utils.DataResult
import core.data.utils.dataResultSafeApiCall
import features.shop.domain.models.Brand
import features.shop.domain.models.Category
import features.shop.domain.models.FilterOptions
import features.shop.domain.models.Shoe
import features.shop.domain.models.ShoeResponse
import features.shop.domain.repository.ShoesRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments

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

    override suspend fun getShoeById(id: Int): DataResult<Shoe> = dataResultSafeApiCall{
        val response = httpClient.get("$BASE_URL/shoes"){
            url {
                appendPathSegments("$id")
            }
        }
        val shoe = response.body<Shoe>()
        shoe
    }

    override suspend fun getAllShoesFiltered(filterOptions: FilterOptions): DataResult<List<Shoe>> = dataResultSafeApiCall{
        val response = httpClient.get("$BASE_URL/shoes/filter"){
            parameter("category", filterOptions.category)
            parameter("brand", filterOptions.brand)
            parameter("minPrice", filterOptions.minPrice)
            parameter("maxPrice", filterOptions.maxPrice)
            parameter("sortBy", filterOptions.sortBy)
            parameter("sortOrder", filterOptions.sortOrder)
            parameter("page", filterOptions.page)
            parameter("pageSize", filterOptions.pageSize)
        }
        val shoes = response.body<ShoeResponse>()
        shoes.shoes
    }

    override suspend fun filterShoesByCategory(category: String): DataResult<List<Shoe>> = dataResultSafeApiCall{
        val response = httpClient.get("$BASE_URL/shoes/category"){
            parameter("category", category)
        }
        val shoes = response.body<List<Shoe>>()
        shoes
    }

    override suspend fun filterShoesByBrand(brand: String): DataResult<List<Shoe>> = dataResultSafeApiCall{
        val response = httpClient.get("$BASE_URL/shoes/brand"){
            parameter("brand", brand)
        }
        val shoes = response.body<List<Shoe>>()
        shoes
    }

    override suspend fun searchShoes(query: String): DataResult<List<Shoe>> = dataResultSafeApiCall{
        val response = httpClient.get("$BASE_URL/shoes/search"){
            parameter("query", query)
        }
        val shoes = response.body<List<Shoe>>()
        shoes
    }

    override suspend fun getAllBrands(): DataResult<List<Brand>> = dataResultSafeApiCall {
        val response = httpClient.get("$BASE_URL/brands/all")
        val brands = response.body<List<Brand>>()
        brands
    }
}