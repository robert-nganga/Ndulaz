package features.shop.data

import core.data.database.NdulaDatabase
import core.data.database.mappers.toDomain
import core.data.database.mappers.toEntity
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
    private val httpClient: HttpClient,
    private val database: NdulaDatabase
): ShoesRepository {

    private val brandDao = database.brandDao()
    private val categoryDao = database.categoryDao()
    override suspend fun getShoes(page: Int, limit: Int): DataResult<List<Shoe>> = dataResultSafeApiCall {
        val response = httpClient.get("$BASE_URL/shoes/all") {
            parameter("page", page)
            parameter("pageSize", limit)
        }
        val shoeResponse = response.body<ShoeResponse>()
        shoeResponse.shoes
    }
    override suspend fun getCategories(): DataResult<List<Category>> {
        val cachedCategories = categoryDao.getAllCategories()
        return try {
            val response = httpClient.get("$BASE_URL/categories/all")
            val categoryResponse = response.body<List<Category>>()
            categoryDao.updateCachedCategories(categoryResponse.map { it.toEntity() })
            val categories = categoryDao.getAllCategories()
            DataResult.Success(categories.map { it.toDomain() })
        } catch (e: Exception){
            if (cachedCategories.isEmpty()){
                DataResult.Error("Unable to get categories")
            } else {
                DataResult.Success(cachedCategories.map { it.toDomain() })
            }
        }
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

    override suspend fun getAllBrands(): DataResult<List<Brand>> {
        val cachedBrands = brandDao.getAllBrands()
        return try {
            val response = httpClient.get("$BASE_URL/brands/all")
            val remoteBrands = response.body<List<Brand>>()
            brandDao.updateCachedBrands(remoteBrands.map { it.toEntity() })
            val brands = brandDao.getAllBrands().map { it.toDomain() }
            DataResult.Success(brands)
        } catch (e: Exception){
            if (cachedBrands.isEmpty()){
                DataResult.Error("Unable to fetch brands")
            } else {
                DataResult.Success(cachedBrands.map { it.toDomain() })
            }
        }
    }
}