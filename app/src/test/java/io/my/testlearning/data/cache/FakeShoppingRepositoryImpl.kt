package io.my.testlearning.data.cache

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import io.my.testlearning.Database
import io.my.testlearning.ShopEntity
import io.my.testlearning.data.ShoppingRepository
import io.my.testlearning.data.dto.ShoppingItemDto
import io.my.testlearning.data.dto.asDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FakeShoppingRepositoryImpl(
    private val dispatcherIO: CoroutineContext = Dispatchers.IO
): ShoppingRepository {

    private val inMemorySqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
        Database.Schema.create(this)
    }
    private val dao = impl(Database(inMemorySqlDriver).shopEntityQueries)

    override val shopItemsFlow: Flow<List<ShoppingItemDto>> = dao
        .observeAllShoppingItems()
        .map { entity -> entity.map { it.asDto() } }


    override val totalPriceFlow: Flow<Double> = dao.observeTotalPrice()

    override suspend fun insertShoppingItem(
        name: String,
        amount: Long,
        price: Double
    ) = withContext(dispatcherIO) {
        dao.insertShoppingItem(null, name, amount, price, null)
    }

    override suspend fun deleteShoppingItemById(id: Long) = withContext(dispatcherIO) {
        dao.deleteShoppingItem(id)
    }

    override suspend fun searchImagesByQuery(query: String) {

    }

}