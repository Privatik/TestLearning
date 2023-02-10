package io.my.testlearning.data

import io.my.testlearning.ShopEntity
import io.my.testlearning.data.cache.ShoppingDao
import io.my.testlearning.data.dto.ShoppingItemDto
import io.my.testlearning.data.dto.asDto
import io.my.testlearning.data.remote.ShoppingApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ShoppingRepository {
    val shopItemsFlow: Flow<List<ShoppingItemDto>>
    val totalPriceFlow: Flow<Double>

    suspend fun insertShoppingItem(
        name: String,
        amount: Long,
        price: Double,
    )

    suspend fun deleteShoppingItemById(id: Long)

    suspend fun searchImagesByQuery(
        query: String
    )
}

class ShoppingRepositoryImpl @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val shoppingApi: ShoppingApi,
): ShoppingRepository {

    override val shopItemsFlow: Flow<List<ShoppingItemDto>> =
        shoppingDao
            .observeAllShoppingItems()
            .map { entity -> entity.map { it.asDto() } }

    override val totalPriceFlow: Flow<Double> =
        shoppingDao.observeTotalPrice()

    override suspend fun insertShoppingItem(name: String, amount: Long, price: Double) {
        shoppingDao.insertShoppingItem(
            id = null,
            name = name,
            amount = amount,
            price = price,
            imageUrl = null
        )
    }

    override suspend fun deleteShoppingItemById(id: Long) {
        shoppingDao.deleteShoppingItem(id)
    }

    override suspend fun searchImagesByQuery(query: String) {
        shoppingApi.searchImagesByQuery(query)
            .onSuccess {  }
    }

}