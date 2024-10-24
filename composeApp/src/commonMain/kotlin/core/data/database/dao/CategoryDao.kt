package core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import core.data.database.entities.CategoryEntity


@Dao
interface CategoryDao {
    @Insert
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Int): CategoryEntity?

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()

    @Transaction
    suspend fun updateCachedCategories(newCategories: List<CategoryEntity>){
        deleteAllCategories()
        insertCategories(newCategories)
    }



}