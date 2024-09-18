package features.shop.domain.repository

import core.data.utils.DataResult
import features.shop.domain.models.Brand
import features.shop.domain.models.Category
import features.shop.domain.models.FilterOptions
import features.shop.domain.models.Shoe

interface ShoesRepository {
    suspend fun getShoes(page: Int, limit: Int): DataResult<List<Shoe>>
    suspend fun getCategories(): DataResult<List<Category>>
    suspend fun getShoeById(id: Int): DataResult<Shoe>
    suspend fun getAllShoesFiltered(filterOptions: FilterOptions): DataResult<List<Shoe>>
    suspend fun filterShoesByCategory(category: String): DataResult<List<Shoe>>
    suspend fun filterShoesByBrand(brand: String): DataResult<List<Shoe>>
    suspend fun searchShoes(query: String): DataResult<List<Shoe>>
    suspend fun getAllBrands(): DataResult<List<Brand>>
}