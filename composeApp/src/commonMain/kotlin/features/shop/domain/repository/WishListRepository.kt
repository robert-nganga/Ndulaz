package features.shop.domain.repository

import core.data.utils.DataResult
import features.shop.domain.models.WishList

interface WishListRepository {
    suspend fun addItemToWishList(shoeId: Int): DataResult<WishList>
    suspend fun removeItemFromWishList(shoeId: Int): DataResult<WishList>
    suspend fun clearWishList(): DataResult<WishList>
    suspend fun getWishList(): DataResult<WishList>
}