package io.my.testlearning

import io.my.testlearning.data.ShoppingRepository
import io.my.testlearning.data.dto.ShoppingItemDto
import kotlinx.coroutines.flow.*

class FakeRepository: ShoppingRepository {
    val _shopItemsFlow = MutableStateFlow<List<ShoppingItemDto>>(emptyList())
    val _imagesFlow = MutableStateFlow<List<String>>(emptyList())

    override val shopItemsFlow: Flow<List<ShoppingItemDto>>
        get() = _shopItemsFlow.asStateFlow()
    override val totalPriceFlow: Flow<Double>
        get() = _shopItemsFlow.map { list -> list.sumOf { it.price * it.amount } }
    override val errorFlow: Flow<String>
        get() = flow {  }
    override val imagesFlow: Flow<List<String>>
        get() = _imagesFlow

    override suspend fun insertShoppingItem(
        name: String,
        amount: Long,
        price: Double,
        imageUrl: String?
    ) {
        val arr = _shopItemsFlow.value
        val newItem = ShoppingItemDto(
            id = arr.size + 1L,
            name, amount, price, imageUrl
        )
        _shopItemsFlow.emit(arr + newItem)
    }

    override suspend fun deleteShoppingItemById(id: Long) {

    }

    override suspend fun addImageUrlById(id: Long, url: String) {

    }

    override suspend fun searchImagesByQuery(query: String) {
        _imagesFlow.emit(buildList { repeat(10) { add("") } })
    }
}