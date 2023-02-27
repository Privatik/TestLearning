package io.my.testlearning.data.cache

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import io.my.testlearning.ShopEntity
import io.my.testlearning.ShopEntityQueries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun impl(
    queries: ShopEntityQueries
): ShoppingDao{
    return ShoppingDaoImpl(queries)
}

interface ShoppingDao {

    suspend fun insertShoppingItem(
        id: Long?,
        name: String,
        amount: Long,
        price: Double,
        imageUrl: String?
    )

    suspend fun deleteShoppingItem(entityId: Long)

    suspend fun addImageUrlById(id: Long, url: String)

    fun observeAllShoppingItems(): Flow<List<ShopEntity>>

    fun observeTotalPrice(): Flow<Double>
}

private class ShoppingDaoImpl(
    private val queries: ShopEntityQueries
): ShoppingDao {

    override suspend fun insertShoppingItem(
        id: Long?,
        name: String,
        amount: Long,
        price: Double,
        imageUrl: String?
    ) {
        queries.insertOrReplaceShoppingItem(
            id, name, amount, price, imageUrl
        )
    }

    override suspend fun deleteShoppingItem(entityId: Long) {
        queries.deleteShoppingItem(entityId)
    }

    override suspend fun addImageUrlById(id: Long, url: String) {
        queries.updateImageUrlShoppingItem(url, id)
    }

    override fun observeAllShoppingItems(): Flow<List<ShopEntity>> =
        queries.selectAllShoppingItems().asFlow().mapToList()

    override fun observeTotalPrice(): Flow<Double> =
        queries.selectTotalPrice().asFlow().mapToOne().map { it.SUM ?: 0.0 }
}