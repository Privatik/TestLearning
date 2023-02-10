package io.my.testlearning.ui.presenter

import androidx.compose.runtime.Immutable
import io.my.testlearning.data.dto.ShoppingItemDto
import io.my.testlearning.util.ActionIntent
import io.my.testlearning.util.createIntent
import kotlinx.coroutines.CoroutineScope

@Immutable
data class ShoppingState(
    val items: List<ShoppingItemDto> = emptyList(),
    val totalPrice: Double = 0.0,
)

class ShoppingIntent(coroutineScope: CoroutineScope) : ActionIntent(coroutineScope) {
    val addItem = createIntent<ShoppingItemInsert>("addItem", coroutineScope)
    val removeItem = createIntent<Long>("removeItem", coroutineScope)

    data class ShoppingItemInsert(
        val name: String,
        val amount: Long,
        val price: Double,
    )
}