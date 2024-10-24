package core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import core.data.database.entities.BrandEntity
import features.shop.domain.models.Brand


@Dao
interface BrandDao {
    @Insert
    suspend fun insertBrands(brands: List<BrandEntity>)

    @Query("SELECT * FROM brands")
    suspend fun getAllBrands(): List<BrandEntity>

    @Query("DELETE FROM brands")
    suspend fun deleteAllBrands()

    @Query("SELECT * FROM brands WHERE id = :id")
    suspend fun getBrandById(id: Int): Brand?

    @Transaction
    suspend fun updateCachedBrands(newBrands: List<BrandEntity>){
        deleteAllBrands()
        insertBrands(newBrands)
    }

}