package io.my.testlearning.ui.presenter

import com.example.machine.ReducerDSL
import io.my.testlearning.data.ShoppingRepository
import io.my.testlearning.util.Presenter
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ShoppingPresenter @Inject constructor(
    private val repository: ShoppingRepository
): Presenter<ShoppingState,ShoppingIntent,Unit>(ShoppingState()) {

    override fun CoroutineScope.buildIntent(): ShoppingIntent = ShoppingIntent(this)

    override fun ReducerDSL<ShoppingState, Unit>.reducer() {
        onEach(
            flow = intent.addItem.asFlow(),
            action = { _, _, payload ->
                println("add $payload")
                repository.insertShoppingItem(
                    payload.name,
                    payload.amount,
                    payload.price,
                )
            }
        )

        onEach(
            flow = intent.removeItem.asFlow(),
            action = { _, _, payload ->
                println("remove $payload")
                repository.deleteShoppingItemById(payload)
            },
        )

        onEach(
            flow = repository.shopItemsFlow,
            changeState = { oldState, payload ->
                println("update items $payload")
                oldState.copy(items = payload)
            },
        )

        onEach(
            flow = repository.totalPriceFlow,
            changeState = { oldState, payload ->
                println("update total $payload")
                oldState.copy(totalPrice = payload)
            },
        )

    }
}