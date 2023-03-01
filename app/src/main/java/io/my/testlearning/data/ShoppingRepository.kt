package io.my.testlearning.data

import io.my.testlearning.data.cache.ShoppingDao
import io.my.testlearning.data.dto.ShoppingItemDto
import io.my.testlearning.data.dto.asDto
import io.my.testlearning.data.remote.ShoppingApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface ShoppingRepository {
    val shopItemsFlow: Flow<List<ShoppingItemDto>>
    val totalPriceFlow: Flow<Double>
    val errorFlow: Flow<String>
    val imagesFlow: Flow<List<String>>

    suspend fun insertShoppingItem(
        name: String,
        amount: Long,
        price: Double,
        imageUrl: String?,
    )

    suspend fun deleteShoppingItemById(id: Long)

    suspend fun addImageUrlById(id: Long, url: String)

    suspend fun searchImagesByQuery(
        query: String
    )
}

class ShoppingRepositoryImpl @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val shoppingApi: ShoppingApi,
    private val dispatcherIO: CoroutineContext,
): ShoppingRepository {

    override val shopItemsFlow: Flow<List<ShoppingItemDto>> =
        shoppingDao
            .observeAllShoppingItems()
            .map { entity -> entity.map { it.asDto() } }

    override val totalPriceFlow: Flow<Double> =
        shoppingDao.observeTotalPrice()

    private val _errorFlow = MutableSharedFlow<String>()
    override val errorFlow: Flow<String> = _errorFlow.asSharedFlow()

    private val _imagesFlow = MutableSharedFlow<List<String>>()
    override val imagesFlow: Flow<List<String>> = _imagesFlow.asSharedFlow()

    override suspend fun insertShoppingItem(
        name: String,
        amount: Long,
        price: Double,
        imageUrl: String?
    ) =
        withContext(dispatcherIO) {
            shoppingDao.insertShoppingItem(
                id = null,
                name = name,
                amount = amount,
                price = price,
                imageUrl = imageUrl
            )
        }

    override suspend fun deleteShoppingItemById(id: Long) = withContext(dispatcherIO) {
        shoppingDao.deleteShoppingItem(id)
    }

    override suspend fun addImageUrlById(id: Long, url: String) = withContext(dispatcherIO) {
        shoppingDao.addImageUrlById(id, url)
    }

    override suspend fun searchImagesByQuery(query: String) = withContext(dispatcherIO) {
        shoppingApi.searchImagesByQuery(query)
            .onSuccess {
                println("")
                _imagesFlow.emit(it.hits.map { hint -> hint.userImageURL })
            }
            .onFailure { _errorFlow.emit(it.message ?: "error") }
        Unit
    }

}