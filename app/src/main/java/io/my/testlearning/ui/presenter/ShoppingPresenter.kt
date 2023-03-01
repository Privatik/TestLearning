@file:OptIn(FlowPreview::class)

package io.my.testlearning.ui.presenter

import com.example.machine.ReducerDSL
import io.my.testlearning.data.ShoppingRepository
import io.my.testlearning.util.Presenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

class ShoppingPresenter @Inject constructor(
    private val repository: ShoppingRepository
): Presenter<ShoppingState,ShoppingIntent,Unit>(ShoppingState()) {

    override fun CoroutineScope.buildIntent(): ShoppingIntent = ShoppingIntent(this)

    override fun ReducerDSL<ShoppingState, Unit>.reducer() {
        onEach(
            flow = intent.addItem.asFlow(),
            action = { _, _, payload ->
                repository.insertShoppingItem(
                    payload.name,
                    payload.amount,
                    payload.price,
                    payload.imageUrl,
                )
            }
        )

        onEach(
            flow = intent.removeItem.asFlow(),
            action = { _, _, payload ->
                repository.deleteShoppingItemById(payload)
            },
        )

        onEach(
            flow = repository.shopItemsFlow,
            changeState = { oldState, payload ->
                oldState.copy(items = payload)
            },
        )

        onEach(
            flow = intent.openBottomBar.asFlow(),
            changeState = { oldState, payload ->
                oldState.copy(isOpenBottomBar = payload)
            },
        )

        onEach(
            flow = repository.errorFlow,
            action = { _, _, _ ->

            },
        )

        onEach(
            flow = repository.imagesFlow,
            changeState = { oldState, payload ->
                oldState.copy(images = payload)
            },
        )

        onEach(
            flow = intent.searchImages.asFlow(),
            action = { _, _, payload ->
                repository.searchImagesByQuery(payload)
            },
        )

        onEach(
            flow = repository.totalPriceFlow,
            changeState = { oldState, payload ->
                oldState.copy(totalPrice = payload)
            },
        )

        onEach(
            flow = intent.saveImage.asFlow(),
            action = { _, _, payload ->
                repository.addImageUrlById(payload.first, payload.second)
            },
        )

    }
}