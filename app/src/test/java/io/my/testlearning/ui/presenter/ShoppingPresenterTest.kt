package io.my.testlearning.ui.presenter

import com.google.common.truth.Truth.assertThat
import io.my.testlearning.MainCoroutineRule
import io.my.testlearning.ShopEntity
import io.my.testlearning.awaitTerminateFlow
import io.my.testlearning.di.DaggerTestAppComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.properties.Delegates

@OptIn(ExperimentalCoroutinesApi::class)
class ShoppingPresenterTest{

    private val shopItems = listOf(
        ShopEntity(1,"Bananas", 2, 0.5, null),
        ShopEntity(2,"Bananas", 4, 0.5, null),
        ShopEntity(3,"Bananas", 6, 0.5, null),
    )
    private var presenter: ShoppingPresenter by Delegates.notNull()
    private var state: StateFlow<ShoppingState> by Delegates.notNull()

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    @Before
    fun setUp(){
        val component = DaggerTestAppComponent.builder().build()
        presenter = component.factory().create(ShoppingPresenter::class.java)
        state = presenter.state
    }

    @Test
    fun totalPrice_return6() = runTest(mainDispatcherRule.dispatcher) {
        launch {
            shopItems.forEach {
                it.addItem()
            }
        }

        state.awaitTerminateFlow()

        assertThat(state.value.totalPrice).isEqualTo(6.0)
    }

    @Test
    fun totalPrice_return0() = runTest(mainDispatcherRule.dispatcher) {
        launch {
            shopItems.forEach {
                it.addItem()
            }
        }

        launch {
            shopItems.forEach {
                presenter.intent.removeItem(it.id)
            }
        }

        state.awaitTerminateFlow()

        assertThat(state.value.totalPrice).isEqualTo(0.0)
    }

    @Test
    fun addItemAndUpdateImage() = runTest(mainDispatcherRule.dispatcher) {
        launch {
            shopItems[0].addItem()
        }

        state.awaitTerminateFlow()

        launch {
            presenter.intent.searchImages("logo")
        }

        state.awaitTerminateFlow()

        launch {
            presenter.intent.saveImage(
                state.value.items.first { it.id == 1L }.id to state.value.images[0]
            )
        }

        state.awaitTerminateFlow()

        assertThat(state.value.items.first { it.id == 1L }.imageUrl).contains("logo")
    }

    @Test
    fun doRequest_returnEmpty() = runTest(mainDispatcherRule.dispatcher) {
        launch {
            presenter.intent.searchImages("")
        }

        state.awaitTerminateFlow()

        assertThat(state.value.images).isEmpty()
    }

    @Test
    fun doRequest_returnLogoUrl() = runTest(mainDispatcherRule.dispatcher) {
        launch {
            presenter.intent.searchImages("logo")
        }

        state.awaitTerminateFlow()

        assertThat(state.value.images[0]).contains("logo")
    }

    private fun ShopEntity.addItem(){
        presenter.intent.addItem(
            ShoppingIntent.ShoppingItemInsert(
                name, amount, price
            )
        )
    }
}